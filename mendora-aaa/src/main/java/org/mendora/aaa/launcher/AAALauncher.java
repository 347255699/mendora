package org.mendora.aaa.launcher;

import com.hazelcast.config.*;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.Vertx;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.mendora.aaa.constant.AAAConst;
import org.mendora.base.BaseLauncher;
import org.mendora.base.properties.ConfigHolder;
import org.mendora.base.scanner.SimplePackageScanner;
import org.mendora.base.utils.VertxHolder;
import org.mendora.base.verticles.SimpleVerticle;
import rx.Observable;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
public class AAALauncher {
    private static final String MODULE_NAME = "INIT:";
    private static Logger logger = LoggerFactory.getLogger(AAALauncher.class);
    // 入口
    public static void launch(URL rootUrl) {
        try {
            BaseLauncher.launch(rootUrl);
            logger.info(MODULE_NAME + "initialization logger and config properties");
        } catch (Exception e) {
            logger.error(MODULE_NAME + e.getMessage());
        }
        clusterVertx();
    }

    /**
     * 启动Vertx 微内核 部署Vertx Verticle
     */

    private static void clusterVertx() {
        // 感应间隔时间，单位秒
        String intervalSeconds = ConfigHolder.property(AAAConst.AAA_HAZELCAST_HEARBEAT_INTERVAL_SECONDS);
        /** hazelcast配置 **/
        Config config = new Config();
        config.setProperty("hazelcast.logging.type", AAAConst.AAA_HAZELCAST_LOGGER_TYPE);
        config.setProperty("hazelcast.heartbeat.interval.seconds", intervalSeconds);
        TcpIpConfig tcpIpConfig = new TcpIpConfig()
                .setEnabled(true)
                .setMembers(Arrays.asList(ConfigHolder.property(AAAConst.AAA_CLUSTER_SERVER_IPS).split(",")));
        JoinConfig joinConfig = new JoinConfig()
                .setMulticastConfig(new MulticastConfig().setEnabled(false))
                .setTcpIpConfig(tcpIpConfig);
        NetworkConfig networkConfig = new NetworkConfig()
                .setPort(Integer.parseInt(ConfigHolder.property(AAAConst.AAA_CLUSTER_PORT)))
                .setJoin(joinConfig);
        config.setNetworkConfig(networkConfig);

        // 设置启动参数，启动Vertx集群
        VertxOptions options = new VertxOptions().setClusterManager(new HazelcastClusterManager(config));
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                VertxHolder.setVertx(vertx);
                // 扫描Verticle
                scanningVerticle(vertx);
            }
        });
    }

    // 扫描Verticle
    private static void scanningVerticle(Vertx vertx) {
        try {
            ClassLoader currClassLoader = AAALauncher.class.getClassLoader();
            String packageName = ConfigHolder.property(AAAConst.AAA_VERTICLE_INTO_PACKAGE);
            List<SimpleVerticle> verticles = new SimplePackageScanner<SimpleVerticle>(packageName, currClassLoader)
                    .scan(SimpleVerticle.class);
            logger.info(MODULE_NAME + verticles.size());
            Observable.from(verticles)
                    .subscribe(v -> vertx.getDelegate().deployVerticle(v, v.options()),
                            err -> logger.error(MODULE_NAME + err.getMessage()),
                            () -> logger.info(MODULE_NAME + "all the \"verticle\" deployed"));
            verticles.forEach(v -> logger.info(v.getClass().getName()));
        } catch (Exception e) {
            logger.error(MODULE_NAME + e.getMessage());
        }
    }
}
