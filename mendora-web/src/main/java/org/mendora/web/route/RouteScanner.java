package org.mendora.web.route;

import com.google.inject.Inject;
import com.google.inject.Injector;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.web.Router;
import lombok.extern.slf4j.Slf4j;
import org.mendora.guice.properties.ConfigHolder;
import org.mendora.guice.scanner.PackageScannerImpl;
import org.mendora.web.constant.WebConst;
import rx.Observable;

import java.util.List;

/**
 * Created by kam on 2018/3/18.
 */
@Slf4j
public class RouteScanner {
    private static final String MODULE_NAME = "ROUTE-SCANNER:";
    private ConfigHolder configHolder;
    private Vertx vertx;
    private Router router;

    @Inject
    public RouteScanner(ConfigHolder configHolder, Vertx vertx, Router router) {
        this.configHolder = configHolder;
        this.vertx = vertx;
        this.router = router;
    }

    public void sann(String packagePath, Injector injector, ClassLoader cl) {
        List<String> names = new PackageScannerImpl<Route>(packagePath, cl)
                .classNames(Route.class.getName(), this.getClass().getName());
        log.info(MODULE_NAME + names.size());
        Observable.from(names)
                .flatMap(name -> {
                    try {
                        Route r = (Route) injector.getInstance(Class.forName(name));
                        return Observable.just(r);
                    } catch (ClassNotFoundException e) {
                        return Observable.error(e);
                    }
                })
                .subscribe(r -> {
                            r.route();
                            log.info(MODULE_NAME + r.getClass().getName());
                        },
                        err -> log.error(MODULE_NAME + err.getMessage()),
                        () -> {
                            String webServerPort = configHolder.property(WebConst.AAA_WEB_LISTENNING_PORT);
                            log.info(MODULE_NAME + "all the \"routes\" deployed");
                            router.getRoutes().forEach(r -> log.info(r.getPath()));
                            vertx.createHttpServer().requestHandler(router::accept)
                                    .listen(Integer.parseInt(webServerPort));
                            log.info(MODULE_NAME + "web server listenning at port:" + webServerPort);
                        });
    }
}
