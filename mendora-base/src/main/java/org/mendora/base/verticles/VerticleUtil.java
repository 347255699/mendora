package org.mendora.base.verticles;

import io.vertx.rxjava.core.Vertx;
import org.mendora.base.properties.ConfigHolder;
import org.mendora.base.scanner.SimplePackageScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.List;

/**
 * created by:xmf
 * date:2018/3/12
 * description:
 */
public class VerticleUtil {
    private static final String MODULE_NAME = "VERTICLE:";
    private static Logger logger = LoggerFactory.getLogger(VerticleUtil.class);

    // 扫描Verticle
    public static void scanningVerticle(Vertx vertx, ClassLoader currClassLoader, String packagePath) {
        try {
            String packageName = ConfigHolder.property(packagePath);
            List<SimpleVerticle> verticles = new SimplePackageScanner<SimpleVerticle>(packageName, currClassLoader)
                    .scan(SimpleVerticle.class);
            logger.info(MODULE_NAME + verticles.size());
            Observable.from(verticles)
                    .subscribe(v -> vertx.getDelegate().deployVerticle(v, v.options()),
                            err -> logger.error(MODULE_NAME + err.getMessage()),
                            () -> logger.info(MODULE_NAME + "all the \"verticle\" deployed"));
            verticles.forEach(v -> logger.info(v.getClass().getName()));
        } catch (Exception e) {
            logger.error(MODULE_NAME + e.getMessage());
        }
    }
}
