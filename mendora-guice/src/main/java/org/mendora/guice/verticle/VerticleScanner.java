package org.mendora.guice.verticle;

import com.google.inject.Inject;
import com.google.inject.Injector;
import io.vertx.core.json.JsonArray;
import io.vertx.rxjava.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.mendora.guice.properties.BaseConst;
import org.mendora.guice.properties.ConfigHolder;
import org.mendora.util.result.JsonResult;
import org.mendora.util.scanner.PackageScannerImpl;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;

/**
 * created by:xmf
 * date:2018/3/12
 * description:
 */
@Slf4j
public class VerticleScanner {
    private static final String MODULE_NAME = "VERTICLE:";
    private static final String DEPLOY_ID = "dId";
    private static final String VERTICLE_NAME = "vName";
    private ConfigHolder configHolder;
    private Vertx vertx;

    @Inject
    public VerticleScanner(ConfigHolder configHolder, Vertx vertx) {
        this.configHolder = configHolder;
        this.vertx = vertx;
    }

    /**
     * scanning verticle
     */
    public void scan(String packagePath, Injector injector, ClassLoader cl) {
        try {
            List<String> names = new PackageScannerImpl<DefaultVerticle>(packagePath, cl)
                    .classNames(DefaultVerticle.class.getName(), this.getClass().getName());
            log.info(MODULE_NAME + names.size());
            val storage = new JsonArray(new ArrayList(names.size()));
            Observable.from(names)
                    .flatMap(name -> {
                        try {
                            DefaultVerticle dv = (DefaultVerticle) injector.getInstance(Class.forName(name));
                            dv.setInjector(injector);
                            return Observable.just(dv);
                        } catch (ClassNotFoundException e) {
                            return Observable.error(e);
                        }
                    })
                    .subscribe(v -> deploy(v, storage),
                            err -> log.error(MODULE_NAME + err.getMessage()),
                            () -> {
                                log.info(MODULE_NAME + "all the \"verticle\" deployed");
                                configHolder.setProperty(BaseConst.BASE_VERTICLE_STORAGE_KEY, storage);
                            });
            names.forEach(log::info);
        } catch (Exception e) {
            log.error(MODULE_NAME + e.getMessage());
        }
    }

    /**
     * deploy verticle and record info.
     *
     * @param verticle
     * @param storage
     */
    private void deploy(DefaultVerticle verticle, JsonArray storage) {
        vertx.getDelegate().deployVerticle(verticle, verticle.options(), res -> {
            if (res.succeeded()) {
                String dId = res.result();
                String vName = verticle.getClass().getName();
                storage.add(JsonResult.allocate(2).put(DEPLOY_ID, dId).put(VERTICLE_NAME, vName));
            } else {
                log.error(MODULE_NAME + res.cause().getMessage());
            }
        });
    }
}
