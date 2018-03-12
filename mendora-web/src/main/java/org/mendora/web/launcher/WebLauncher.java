package org.mendora.web.launcher;

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

    // 入口
    public static void launch(URL rootUrl, ClassLoader cl) {
        try {
            BaseLauncher.launch(rootUrl, cl, vertx -> {
                // has vertx now, we should do something.
            });
            logger.info(MODULE_NAME + "initialization logger and config properties");
        } catch (Exception e) {
            logger.error(MODULE_NAME + e.getMessage());
        }
    }


}
