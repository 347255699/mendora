package org.mendora.aider.launcher;

import lombok.extern.slf4j.Slf4j;
import org.mendora.guice.GuiceLauncher;

import java.net.URL;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
@Slf4j
public class AiderLauncher {
    private static final String MODULE_NAME = "INIT:";

    // entrance
    public static void launch(URL rootUrl) {
        try {
            GuiceLauncher.launch(rootUrl, AiderLauncher.class.getClassLoader());
            log.info("{}initialization logger and config properties", MODULE_NAME);
        } catch (Exception e) {
            log.error(MODULE_NAME + e.getMessage());
        }
    }
}
