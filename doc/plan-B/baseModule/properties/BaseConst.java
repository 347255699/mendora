package org.mendora.base.properties;

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
    String BASE_LOGGER_FACTORY_CLASS_NAME = "base.logger.factory.class.name";
    String BASE_LOGGER_CONFIG_PATH = "base.logger.config.path";
    String BASE_VERTICLE_INTO_PACKAGE = "base.verticle.into.package";
    String BASE_VERTICLE_STORAGE_KEY = "base.verticles.storage.key";
    String BASE_SYSTEM_ROOT_PATH = "base.system.rootPath";

    // hazelcast
    String BASE_HAZELCAST_LOGGER_TYPE = "base.hazelcast.logger.type";
    String BASE_HAZELCAST_HEARBEAT_INTERVAL_SECONDS = "base.hazelcast.heartbeat.interval.seconds";
    String BASE_CLUSTER_PORT = "base.cluster.port";
    String BASE_CLUSTER_SERVER_IPS = "base.cluster.server.ips";

}
