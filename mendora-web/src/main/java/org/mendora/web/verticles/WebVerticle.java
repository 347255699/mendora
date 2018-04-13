package org.mendora.web.verticles;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertx.rxjava.ext.web.handler.CorsHandler;
import io.vertx.rxjava.ext.web.handler.LoggerHandler;
import lombok.extern.slf4j.Slf4j;
import org.mendora.guice.scanner.route.RouteScanner;
import org.mendora.guice.verticles.DefaultVerticle;
import org.mendora.service.facade.constant.FacadeConst;
import org.mendora.service.facade.scanner.ServiceRxProxyBinder;
import org.mendora.service.facade.scanner.ServiceRxProxyScanner;
import org.mendora.web.auth.WebAuth;
import org.mendora.web.binder.WebBinder;
import org.mendora.web.constant.WebConst;

import java.util.Arrays;
import java.util.List;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
@Slf4j
public class WebVerticle extends DefaultVerticle {
    private static final String MODULE_NAME = "WEB-VERTICLE:";

    @Override
    public DeploymentOptions options() {
        return super.options();
    }

    @Override
    public void start() {
        log.info(MODULE_NAME + "into WebVerticle");
        Router router = Router.router(vertx);
        WebAuth webAuth = new WebAuth(vertx, configHolder);

        // injecting your bean into WebBinder class
        String proxyIntoPackage = configHolder.property(FacadeConst.FACADE_SERVICE_PROXY_INTO_PACKAGE);
        ServiceRxProxyBinder serviceRxProxyBinder = new ServiceRxProxyScanner().scan(proxyIntoPackage, injector);
        injector = injector.createChildInjector(serviceRxProxyBinder, new WebBinder(router, webAuth));

        // before routing request
        beforeRoutingRequest(router, "*");

        // scanning route
        RouteScanner scanner = injector.getInstance(RouteScanner.class);
        scanner.scan(configHolder.property(WebConst.WEB_ROUTE_INTO_PACKAGE), injector, WebVerticle.class.getClassLoader());
    }

    /**
     * setting handler before routing request
     */
    private void beforeRoutingRequest(Router router, String accessDomain) {
        // use cors
        int maxAgeSeconds = Integer.parseInt(configHolder.property(WebConst.WEB_CORS_MAX_AGE_SECONDS));
        CorsHandler corsHandler = CorsHandler.create(accessDomain).maxAgeSeconds(maxAgeSeconds);
        List<String> methods = Arrays.asList(configHolder.property(WebConst.WEB_CORS_ALLOWED_METHODS).split(","));
        methods.forEach(name -> corsHandler.allowedMethod(HttpMethod.valueOf(name)));
        List<String> headers = Arrays.asList(configHolder.property(WebConst.WEB_CORS_ALLOWED_HEADERS).split(","));
        headers.forEach(corsHandler::allowedHeader);
        router.route().handler(corsHandler);
        // use http request logging.
        router.route().handler(LoggerHandler.create(LoggerFormat.TINY));
        // use http request body as Json,Buffer,String.
        long bodyLimit = Long.parseLong(configHolder.property(WebConst.WEB_REQUEST_BODY_SIZE));
        router.route().handler(BodyHandler.create().setBodyLimit(bodyLimit));
    }
}
