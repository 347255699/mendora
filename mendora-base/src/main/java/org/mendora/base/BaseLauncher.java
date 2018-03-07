package org.mendora.base;

import org.apache.log4j.PropertyConfigurator;
import org.mendora.base.properties.BaseConst;
import org.mendora.base.properties.ConfigHolder;

import java.net.URL;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
public class BaseLauncher {
    public static void launch(URL rootUrl) throws Exception {
        // initialization config properties
        ConfigHolder.init();
        // loading app root path
        String rootPath = rootUrl.getPath().substring(0, rootUrl.getPath().lastIndexOf("/"));
        ConfigHolder.setProperty(BaseConst.BASE_ROOT_PATH, rootPath);
        System.setProperty("rootPath", rootPath);
        // initialization logger
        System.setProperty("vertx.logger-delegate-factory-class-name", ConfigHolder.property(BaseConst.BASE_LOGGER_FACTORY_CLASS_NAME));
        String log4jConfigPath = rootPath + ConfigHolder.property(BaseConst.BASE_LOGGER_CONFIG_PATH);
        PropertyConfigurator.configure(log4jConfigPath);
    }
}
