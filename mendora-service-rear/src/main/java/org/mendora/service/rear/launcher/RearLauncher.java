package org.mendora.service.rear.launcher;

import lombok.extern.slf4j.Slf4j;
import org.mendora.guice.GuiceLauncher;

import java.net.URL;

/**
 * created by:xmf
 * date:2018/3/12
 * description:
 */
@Slf4j
public class RearLauncher {
    private static final String MODULE_NAME = "INIT:";

    // entrance
    public static void launch(URL rootUrl) {
        try {
            GuiceLauncher.launch(rootUrl, RearLauncher.class.getClassLoader());
            log.info(MODULE_NAME + "initialization logger and config properties");
        } catch (Exception e) {
            log.error(MODULE_NAME + e.getMessage());
        }
    }
}
