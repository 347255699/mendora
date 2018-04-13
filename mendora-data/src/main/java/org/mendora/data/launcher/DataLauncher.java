package org.mendora.data.launcher;

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
            GuiceLauncher.launch(rootUrl, DataLauncher.class.getClassLoader());
            log.info("{}initialization logger and config properties", MODULE_NAME);
        } catch (Exception e) {
            log.error(MODULE_NAME + e.getMessage());
        }
    }
}
