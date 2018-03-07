package org.mendora.aaa.verticles;

import io.vertx.core.DeploymentOptions;
import io.vertx.ext.web.Router;
import org.mendora.aaa.constant.AAAConst;
import org.mendora.aaa.route.Route;
import org.mendora.base.properties.ConfigHolder;
import org.mendora.base.scanner.PackageSimpleScanner;
import org.mendora.base.utils.BaseLogger;
import org.mendora.base.verticles.SimpleVerticle;
import rx.Observable;

import java.util.Set;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
public class WebVerticle extends SimpleVerticle {
    private static final String MODULE_NAME = "WEB-VERTICLE:";

    @Override
    public DeploymentOptions options() {
        return super.options();
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        // scanning route
        Set<Route> routes = new PackageSimpleScanner<Route>().scan(ConfigHolder.property(AAAConst.AAA_WEB_ROUTE_PACKAGE), Route.class);
        Observable.from(routes)
                .subscribe(r -> r.route(router),
                        err -> BaseLogger.error(MODULE_NAME + err.getMessage()),
                        () -> {
                            String webServerPort = ConfigHolder.property(AAAConst.AAA_WEB_LISTENNING_PORT);
                            BaseLogger.info(MODULE_NAME + "all the \"routes\" deployed");
                            router.getRoutes().forEach(r -> BaseLogger.info(r.getPath()));
                            vertx.createHttpServer().requestHandler(router::accept)
                                    .listen(Integer.parseInt(webServerPort));
                            BaseLogger.info(MODULE_NAME + "web server listenning at port:{}", webServerPort);
                        });
    }
}
