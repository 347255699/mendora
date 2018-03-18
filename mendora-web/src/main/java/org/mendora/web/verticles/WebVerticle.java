package org.mendora.web.verticles;

import com.google.inject.Inject;
import io.vertx.core.DeploymentOptions;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertx.rxjava.ext.web.handler.LoggerHandler;
import lombok.extern.slf4j.Slf4j;
import org.mendora.guice.properties.ConfigHolder;
import org.mendora.guice.verticle.DefaultVerticle;
import org.mendora.web.binder.WebBinder;
import org.mendora.web.constant.WebConst;
import org.mendora.web.route.RouteScanner;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
@Slf4j
public class WebVerticle extends DefaultVerticle {
    private static final String MODULE_NAME = "WEB-VERTICLE:";
    @Inject
    private Vertx vertx;
    @Inject
    private ConfigHolder configHolder;

    @Override
    public DeploymentOptions options() {
        return super.options();
    }

    @Override
    public void start() {
        log.info(MODULE_NAME + "into WebVerticle");
        Router router = Router.router(vertx);
        injector = injector.createChildInjector(new WebBinder(router));
        /** before routing request **/
        // use http request logging.
        router.route().handler(LoggerHandler.create(LoggerFormat.TINY));
        // use http request body as Json,Buffer,String
        long bodyLimit = Long.parseLong(configHolder.property(WebConst.AAA_WEB_REQUEST_BODY_SIZE));
        router.route().handler(BodyHandler.create().setBodyLimit(bodyLimit));
        RouteScanner scanner = injector.getInstance(RouteScanner.class);
        scanner.sann(configHolder.property(WebConst.AAA_WEB_ROUTE_PACKAGE), injector, WebVerticle.class.getClassLoader());
    }
}
