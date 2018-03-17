package org.mendora.web.verticles;

import io.vertx.core.DeploymentOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.ext.web.handler.LoggerHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.mendora.base.properties.ConfigHolder;
import org.mendora.base.scanner.SimplePackageScanner;
import org.mendora.base.verticles.SimpleVerticle;
import org.mendora.web.constant.WebConst;
import org.mendora.web.route.Route;
import rx.Observable;

import java.util.List;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
@Slf4j
public class WebVerticle extends SimpleVerticle {
    private static final String MODULE_NAME = "WEB-VERTICLE:";

    @Override
    public DeploymentOptions options() {
        return super.options();
    }

    @Override
    @SneakyThrows
    public void start(){
        log.info(MODULE_NAME + "into WebVerticle");
        ClassLoader currClassLoader = WebVerticle.class.getClassLoader();
        Router router = Router.router(vertx);

        /** before routing request **/
        // use http request logging.
        router.route().handler(LoggerHandler.create(LoggerFormat.TINY));
        // use http request body as Json,Buffer,String
        long bodyLimit = Long.parseLong(ConfigHolder.property(WebConst.AAA_WEB_REQUEST_BODY_SIZE));
        router.route().handler(BodyHandler.create().setBodyLimit(bodyLimit));

        // scanning route
        List<Route> routes = new SimplePackageScanner<Route>(ConfigHolder.property(WebConst.AAA_WEB_ROUTE_PACKAGE), currClassLoader)
                .scan(Route.class);
        log.info(MODULE_NAME + routes.size());
        Observable.from(routes)
                .subscribe(r -> {
                            r.route(router);
                            log.info(MODULE_NAME + r.getClass().getName());
                        },
                        err -> log.error(MODULE_NAME + err.getMessage()),
                        () -> {
                            String webServerPort = ConfigHolder.property(WebConst.AAA_WEB_LISTENNING_PORT);
                            log.info(MODULE_NAME + "all the \"routes\" deployed");
                            router.getRoutes().forEach(r -> log.info(r.getPath()));
                            vertx.createHttpServer().requestHandler(router::accept)
                                    .listen(Integer.parseInt(webServerPort));
                            log.info(MODULE_NAME + "web server listenning at port:" + webServerPort);
                        });
    }
}
