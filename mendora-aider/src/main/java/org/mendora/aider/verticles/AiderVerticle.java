package org.mendora.aider.verticles;

import io.vertx.core.DeploymentOptions;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertx.rxjava.ext.web.handler.LoggerHandler;
import lombok.extern.slf4j.Slf4j;
import org.mendora.aider.auth.WebAuth;
import org.mendora.aider.binder.AiderBinder;
import org.mendora.aider.constant.AiderConst;
import org.mendora.guice.scanner.route.RouteScanner;
import org.mendora.guice.verticles.DefaultVerticle;
import org.mendora.service.facade.constant.FacadeConst;
import org.mendora.service.facade.scanner.ServiceRxProxyScanner;

import java.util.Arrays;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
@Slf4j
public class AiderVerticle extends DefaultVerticle {
    private static final String MODULE_NAME = "AIDER-VERTICLE:";

    @Override
    public DeploymentOptions options() {
        return super.options();
    }

    @Override
    public void start() {
        log.info("{}into AiderVerticle", MODULE_NAME);
        Router router = Router.router(vertx);
        WebAuth webAuth = new WebAuth(vertx, configHolder);

        // injecting your bean into AiderBinder class
        String proxyIntoPackage = configHolder.property(FacadeConst.FACADE_SERVICE_PROXY_INTO_PACKAGE);
        injector = new ServiceRxProxyScanner().scan(Arrays.asList(proxyIntoPackage.split(",")), injector);
        injector = injector.createChildInjector(new AiderBinder(router, webAuth));

        // before routing request
        beforeRoutingRequest(router);

        // scanning verticles
        RouteScanner scanner = injector.getInstance(RouteScanner.class);
        scanner.scan(configHolder.property(AiderConst.AIDER_ROUTE_INTO_PACKAGE), injector, AiderVerticle.class.getClassLoader());
    }

    /**
     * setting handler before routing request
     */
    private void beforeRoutingRequest(Router router) {
        // use http request logging.
        router.route().handler(LoggerHandler.create(LoggerFormat.TINY));
        // use http request body as Json,Buffer,String.
        long bodyLimit = Long.parseLong(configHolder.property(AiderConst.AIDER_REQUEST_BODY_SIZE));
        router.route().handler(BodyHandler.create().setBodyLimit(bodyLimit));
    }
}
