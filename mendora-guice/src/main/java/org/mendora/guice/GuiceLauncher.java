package org.mendora.guice;

import org.apache.log4j.PropertyConfigurator;
import org.mendora.guice.cluster.Cluster;
import org.mendora.guice.properties.BaseConst;
import org.mendora.guice.properties.ConfigHolder;
import org.mendora.guice.properties.PropertiesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * Created by kam on 2018/3/18.
 */
public class GuiceLauncher {
    public static void launch(URL rootUrl, ClassLoader cl) {
        String rootPath = rootUrl.getPath().substring(0, rootUrl.getPath().lastIndexOf("/"));
        // initialization config properties
        String propPath = rootPath + "/config/config.properties";
        ConfigHolder configHolder = new ConfigHolder(new PropertiesLoader(propPath));
        // loading app root path
        configHolder.setProperty(BaseConst.BASE_ROOT_PATH, rootPath);
        System.setProperty(BaseConst.BASE_ROOT_PATH, rootPath);
        // loading cup number.
        configHolder.setProperty(BaseConst.BASE_AVAILABLE_PROCESSORS, Runtime.getRuntime().availableProcessors());
        // initialization logger
        System.setProperty("vertx.logger-delegate-factory-class-name", configHolder.property(BaseConst.BASE_LOGGER_FACTORY_CLASS_NAME));
        String log4jConfigPath = rootPath + configHolder.property(BaseConst.BASE_LOGGER_CONFIG_PATH);
        PropertyConfigurator.configure(log4jConfigPath);
        Logger logger = LoggerFactory.getLogger(GuiceLauncher.class);
        configHolder.asJson().forEach(e -> {
            logger.info("System options: {}", e.getKey() + " : " + e.getValue());
        });
        // launching cluster.
        Cluster.launch(configHolder, cl);
    }

}
