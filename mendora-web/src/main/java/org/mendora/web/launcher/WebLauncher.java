package org.mendora.web.launcher;

import io.vertx.rxjava.core.Vertx;
import org.mendora.base.BaseLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
public class WebLauncher {
    private static final String MODULE_NAME = "INIT:";
    private static Logger logger = LoggerFactory.getLogger(WebLauncher.class);

    // entrance
    public static void launch(URL rootUrl, ClassLoader cl) {
        try {
            BaseLauncher.launch(rootUrl, cl, WebLauncher::init);
            logger.info(MODULE_NAME + "initialization logger and config properties");
        } catch (Exception e) {
            logger.error(MODULE_NAME + e.getMessage());
        }
    }

    public static void init(Vertx vertx){
        // you has vertx now, you can do everything you like.
    }


}
