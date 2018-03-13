package org.mendora.data.launcher;

import org.mendora.base.BaseLauncher;
import org.mendora.data.client.ClientHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * created by:xmf
 * date:2018/3/12
 * description:
 */
public class DataLauncher {
    private static final String MODULE_NAME = "INIT:";
    private static Logger logger = LoggerFactory.getLogger(DataLauncher.class);

    // entrance
    public static void launch(URL rootUrl, ClassLoader cl) {
        try {
            BaseLauncher.launch(rootUrl, cl, ClientHolder::init);
            logger.info(MODULE_NAME + "initialization logger and config properties");
        } catch (Exception e) {
            logger.error(MODULE_NAME + e.getMessage());
        }
    }

}
