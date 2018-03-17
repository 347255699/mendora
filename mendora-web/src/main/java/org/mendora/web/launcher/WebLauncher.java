package org.mendora.web.launcher;


import io.vertx.rxjava.core.Vertx;

import lombok.extern.slf4j.Slf4j;

import org.mendora.base.BaseLauncher;

import java.net.URL;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
@Slf4j
public class WebLauncher {
    private static final String MODULE_NAME = "INIT:";

    // entrance
    public static void launch(URL rootUrl, ClassLoader cl) {
        try {
            BaseLauncher.launch(rootUrl, cl, WebLauncher::init);
            log.info(MODULE_NAME + "initialization logger and config properties");
        } catch (Exception e) {
            log.error(MODULE_NAME + e.getMessage());
        }
    }

    public static void init(Vertx vertx){
        // you has vertx now, you can do everything you like.
    }

}
