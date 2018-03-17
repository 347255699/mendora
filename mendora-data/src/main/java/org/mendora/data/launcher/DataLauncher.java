package org.mendora.data.launcher;

import io.vertx.rxjava.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.mendora.base.BaseLauncher;
import org.mendora.data.client.ClientHolder;
import org.mendora.data.service.rpcService.DataAccessServiceImpl;
import org.mendora.service.dataAccesser.rxjava.DataAccessService;

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
    public static void launch(URL rootUrl, ClassLoader cl) {
        try {
            BaseLauncher.launch(rootUrl, cl, DataLauncher::init);
            log.info(MODULE_NAME + "initialization logger and config properties");
        } catch (Exception e) {
            log.error(MODULE_NAME + e.getMessage());
        }
    }

    public static void init(Vertx vertx) {
        ClientHolder.init(vertx);
        DataAccessService.register(vertx, new DataAccessService(new DataAccessServiceImpl()));
    }

}
