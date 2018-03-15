package org.mendora.base.verticles;

import io.vertx.core.json.JsonArray;
import io.vertx.rxjava.core.Vertx;
import org.mendora.base.properties.BaseConst;
import org.mendora.base.properties.ConfigHolder;
import org.mendora.base.scanner.SimplePackageScanner;
import org.mendora.util.result.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;

/**
 * created by:xmf
 * date:2018/3/12
 * description:
 */
public class VerticleUtil {
    private static final String MODULE_NAME = "VERTICLE:";
    private static Logger logger = LoggerFactory.getLogger(VerticleUtil.class);
    private static final String DEPLOY_ID = "dId";
    private static final String VERTICLE_NAME = "vName";

    // scanning Verticle
    public static void scanningVerticle(Vertx vertx, ClassLoader currClassLoader, String packagePath) {
        try {
            String packageName = ConfigHolder.property(packagePath);
            List<SimpleVerticle> verticles = new SimplePackageScanner<SimpleVerticle>(packageName, currClassLoader)
                    .scan(SimpleVerticle.class);
            logger.info(MODULE_NAME + verticles.size());
            JsonArray storage = new JsonArray(new ArrayList(verticles.size()));
            Observable.from(verticles)
                    .subscribe(v -> deploy(vertx, v, storage),
                            err -> logger.error(MODULE_NAME + err.getMessage()),
                            () -> {
                                logger.info(MODULE_NAME + "all the \"verticle\" deployed");
                                ConfigHolder.setProperty(BaseConst.BASE_VERTICLE_STORAGE_KEY, storage);
                            });
            verticles.forEach(v -> logger.info(v.getClass().getName()));
        } catch (Exception e) {
            logger.error(MODULE_NAME + e.getMessage());
        }
    }

    /**
     * deploy verticle and record info.
     *
     * @param vertx
     * @param verticle
     * @param storage
     */
    private static void deploy(Vertx vertx, SimpleVerticle verticle, JsonArray storage) {
        vertx.getDelegate().deployVerticle(verticle, verticle.options(), res -> {
            if (res.succeeded()) {
                String dId = res.result();
                String vName = verticle.getClass().getName();
                storage.add(JsonResult.allocate(2).put(DEPLOY_ID, dId).put(VERTICLE_NAME, vName));
            } else {
                logger.error(MODULE_NAME + res.cause().getMessage());
            }
        });
    }
}
