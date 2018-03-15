package org.mendora.base;

import org.apache.log4j.PropertyConfigurator;
import org.mendora.base.cluster.Cluster;
import org.mendora.base.cluster.ClusterHandler;
import org.mendora.base.properties.BaseConst;
import org.mendora.base.properties.ConfigHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
public class BaseLauncher {
    public static void launch(URL rootUrl, ClassLoader cl, ClusterHandler handler) throws Exception {
        String rootPath = rootUrl.getPath().substring(0, rootUrl.getPath().lastIndexOf("/"));
        // initialization config properties
        String configPath = rootPath + "/config/config.properties";
        ConfigHolder.init(configPath);
        // loading app root path
        ConfigHolder.setProperty(BaseConst.BASE_ROOT_PATH, rootPath);
        System.setProperty("system.rootPath", rootPath);
        // initialization logger
        System.setProperty("vertx.logger-delegate-factory-class-name", ConfigHolder.property(BaseConst.BASE_LOGGER_FACTORY_CLASS_NAME));
        String log4jConfigPath = rootPath + ConfigHolder.property(BaseConst.BASE_LOGGER_CONFIG_PATH);
        PropertyConfigurator.configure(log4jConfigPath);
        Logger logger = LoggerFactory.getLogger(BaseLauncher.class);
        ConfigHolder.asJson().forEach(e -> {
            logger.info("System options: {}", e.getKey() + " : " + e.getValue());
        });
        // launching cluster and scanning Verticles
        Cluster.launch(cl, handler);
    }
}
