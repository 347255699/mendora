package org.mendora.data.launcher;

import com.google.inject.Injector;
import lombok.extern.slf4j.Slf4j;
import org.mendora.guice.GuiceLauncher;

import java.net.URL;

/**
 * created by:xmf
 * date:2018/3/12
 * description:
 */
@Slf4j
public class DataLauncher {
    private static final String MODULE_NAME = "INIT:";

    // entrance
    public static void launch(URL rootUrl) {
        try {
            GuiceLauncher.launch(rootUrl, DataLauncher.class.getClassLoader(), DataLauncher::init);
            log.info(MODULE_NAME + "initialization logger and config properties");
        } catch (Exception e) {
            log.error(MODULE_NAME + e.getMessage());
        }
    }

    public static void init(Injector injector) {
        // use injecotr do something you like.
    }

}
