package org.mendora.base.cluster;

import com.hazelcast.config.*;
import io.vertx.core.VertxOptions;
import io.vertx.rxjava.core.Vertx;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.mendora.base.properties.BaseConst;
import org.mendora.base.properties.ConfigHolder;
import org.mendora.base.utils.VertxHolder;
import org.mendora.base.verticles.VerticleUtil;

import java.util.Arrays;

/**
 * created by:xmf
 * date:2018/3/12
 * description:
 */
public class ClusterUtil {
    /**
     * 启动Vertx 微内核 部署Vertx Verticle
     */
    public static void clusterVertx(ClassLoader cl, ClusterHandler handler) {
        // 感应间隔时间，单位秒
        String intervalSeconds = ConfigHolder.property(BaseConst.BASE_HAZELCAST_HEARBEAT_INTERVAL_SECONDS);
        /** hazelcast配置 **/
        Config config = new Config();
        config.setProperty("hazelcast.logging.type", ConfigHolder.property(BaseConst.BASE_HAZELCAST_LOGGER_TYPE));
        config.setProperty("hazelcast.heartbeat.interval.seconds", intervalSeconds);
        TcpIpConfig tcpIpConfig = new TcpIpConfig()
                .setEnabled(true)
                .setMembers(Arrays.asList(ConfigHolder.property(BaseConst.BASE_CLUSTER_SERVER_IPS).split(",")));
        JoinConfig joinConfig = new JoinConfig()
                .setMulticastConfig(new MulticastConfig().setEnabled(false))
                .setTcpIpConfig(tcpIpConfig);
        NetworkConfig networkConfig = new NetworkConfig()
                .setPort(Integer.parseInt(ConfigHolder.property(BaseConst.BASE_CLUSTER_PORT)))
                .setJoin(joinConfig);
        config.setNetworkConfig(networkConfig);

        // 设置启动参数，启动Vertx集群
        VertxOptions options = new VertxOptions().setClusterManager(new HazelcastClusterManager(config));
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                VertxHolder.setVertx(vertx);
                // 扫描Verticle
                VerticleUtil.scanningVerticle(vertx, cl, BaseConst.BASE_VERTICLE_INTO_PACKAGE);
                handler.handle(vertx);
            }
        });
    }
}
