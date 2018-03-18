package org.mendora.guice.cluster;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.hazelcast.config.*;
import io.vertx.core.VertxOptions;
import io.vertx.rxjava.core.Vertx;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.mendora.guice.binder.GuiceVertxBinder;
import org.mendora.guice.properties.BaseConst;
import org.mendora.guice.properties.ConfigHolder;
import org.mendora.guice.verticle.VerticleScanner;

import java.util.Arrays;

/**
 * created by:xmf
 * date:2018/3/12
 * description:
 */
public class Cluster {
    /**
     * launching Vertx micro kernel and deploy verticle.
     */
    public static void launch(ConfigHolder configHolder, ClassLoader cl, ClusterHandler handler) {
        // seconds
        String intervalSeconds = configHolder.property(BaseConst.BASE_HAZELCAST_HEARBEAT_INTERVAL_SECONDS);
        /** hazelcast configuration **/
        Config config = new Config();
        config.setProperty("hazelcast.logging.type", configHolder.property(BaseConst.BASE_HAZELCAST_LOGGER_TYPE));
        config.setProperty("hazelcast.heartbeat.interval.seconds", intervalSeconds);
        TcpIpConfig tcpIpConfig = new TcpIpConfig()
                .setEnabled(true)
                .setMembers(Arrays.asList(configHolder.property(BaseConst.BASE_CLUSTER_SERVER_IPS).split(",")));
        JoinConfig joinConfig = new JoinConfig()
                .setMulticastConfig(new MulticastConfig().setEnabled(false))
                .setTcpIpConfig(tcpIpConfig);
        NetworkConfig networkConfig = new NetworkConfig()
                .setPort(Integer.parseInt(configHolder.property(BaseConst.BASE_CLUSTER_PORT)))
                .setJoin(joinConfig);
        config.setNetworkConfig(networkConfig);

        // configuration and launching Vertx cluster.
        VertxOptions options = new VertxOptions().setClusterManager(new HazelcastClusterManager(config));
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                Injector injector = Guice.createInjector(new GuiceVertxBinder(configHolder, vertx));
                VerticleScanner scanner = injector.getInstance(VerticleScanner.class);
                scanner.scan(configHolder.property(BaseConst.BASE_VERTICLE_INTO_PACKAGE), injector, cl);
                handler.handle(injector);
            }
        });
    }
}
