package org.mendora.web.launcher;

import com.google.inject.Injector;
import lombok.extern.slf4j.Slf4j;
import org.mendora.guice.GuiceLauncher;

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
    public static void launch(URL rootUrl) {
        try {
            GuiceLauncher.launch(rootUrl, WebLauncher.class.getClassLoader(), WebLauncher::init);
            log.info(MODULE_NAME + "initialization logger and config properties");
        } catch (Exception e) {
            log.error(MODULE_NAME + e.getMessage());
        }
    }

    public static void init(Injector injector) {
        // you has vertx now, you can do everything you like.
    }

}
