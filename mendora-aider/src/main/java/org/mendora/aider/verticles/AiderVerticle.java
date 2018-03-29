package org.mendora.aider.verticles;

import io.vertx.core.DeploymentOptions;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertx.rxjava.ext.web.handler.LoggerHandler;
import lombok.extern.slf4j.Slf4j;
import org.mendora.aider.binder.AiderBinder;
import org.mendora.aider.constant.AiderConst;
import org.mendora.guice.scanner.route.RouteScanner;
import org.mendora.guice.verticles.DefaultVerticle;
import org.mendora.service.facade.scanner.ServiceRxProxyBinder;
import org.mendora.service.facade.scanner.ServiceRxProxyScanner;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
@Slf4j
public class AiderVerticle extends DefaultVerticle {
    private static final String MODULE_NAME = "WEB-VERTICLE:";

    @Override
    public DeploymentOptions options() {
        return super.options();
    }

    @Override
    public void start() {
        log.info(MODULE_NAME + "into AiderVerticle");
        Router router = Router.router(vertx);
        // injecting your bean into AiderBinder class
        String proxyIntoPackage = configHolder.property(AiderConst.AIDER_SERVICE_PROXY_INTO_PACKAGE);
        ServiceRxProxyBinder serviceProxyBinder = new ServiceRxProxyScanner().scan(proxyIntoPackage, injector);
        injector = injector.createChildInjector(new AiderBinder(router), serviceProxyBinder);

        /** before routing request **/
        // use http request logging.
        router.route().handler(LoggerHandler.create(LoggerFormat.TINY));
        // use http request body as Json,Buffer,String
        long bodyLimit = Long.parseLong(configHolder.property(AiderConst.AIDER_REQUEST_BODY_SIZE));
        router.route().handler(BodyHandler.create().setBodyLimit(bodyLimit));
        // scanning verticles
        RouteScanner scanner = injector.getInstance(RouteScanner.class);
        scanner.scan(configHolder.property(AiderConst.AIDER_ROUTE_INTO_PACKAGE), injector, AiderVerticle.class.getClassLoader());
    }
}
