package org.mendora.guice.properties;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
public interface BaseConst {
    /**
     * parameter from properties
     */
    // base
    String BASE_ROOT_PATH = "base.root.path";
    String BASE_LOGGER_FACTORY_CLASS_NAME = "base.logger.factory.class.name";
    String BASE_LOGGER_CONFIG_PATH = "base.logger.config.path";
    String BASE_VERTICLE_INTO_PACKAGE = "base.verticle.into.package";
    String BASE_VERTICLE_STORAGE_KEY = "base.verticles.storage.key";
    String BASE_AVAILABLE_PROCESSORS = "base.available.processors";
    String BASE_SERVICE_PROVIDER_INTO_PACKAGE = "base.service.provider.into.package";
            // hazelcast
    String BASE_HAZELCAST_LOGGER_TYPE = "base.hazelcast.logger.type";
    String BASE_HAZELCAST_HEARBEAT_INTERVAL_SECONDS = "base.hazelcast.heartbeat.interval.seconds";
    String BASE_CLUSTER_PORT = "base.cluster.port";
    String BASE_CLUSTER_SERVER_IPS = "base.cluster.server.ips";
    // web
    String BASE_WEB_LISTENNING_PORT = "base.web.listenning.port";

}
