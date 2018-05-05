package org.mendora.web.verticles;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertx.rxjava.ext.web.handler.CorsHandler;
import io.vertx.rxjava.ext.web.handler.LoggerHandler;
import lombok.extern.slf4j.Slf4j;
import org.mendora.guice.scanner.route.RouteScanner;
import org.mendora.guice.verticles.DefaultVerticle;
import org.mendora.service.facade.constant.FacadeConst;
import org.mendora.service.facade.dataAccesser.mongo.rxjava.MongoAccesser;
import org.mendora.service.facade.scanner.ServiceRxProxyScanner;
import org.mendora.util.constant.MongoCol;
import org.mendora.util.generate.MongoAccesserUtils;
import org.mendora.util.result.JsonResult;
import org.mendora.web.auth.WebAuth;
import org.mendora.web.binder.WebBinder;
import org.mendora.web.constant.WebConst;
import org.mendora.web.efficiency.result.WebResult;

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
        log.info("{}into WebVerticle", MODULE_NAME);
        Router router = Router.router(vertx);
        WebAuth webAuth = new WebAuth(vertx, configHolder);

        // injecting service proxy
        String proxyIntoPackage = configHolder.property(FacadeConst.FACADE_SERVICE_PROXY_INTO_PACKAGE);
        injector = new ServiceRxProxyScanner().scan(Arrays.asList(proxyIntoPackage.split(",")), injector);

        // injecting your bean into WebBinder class
        MongoAccesser mongoAccesser = injector.getInstance(MongoAccesser.class);
        WebResult webResult = new WebResult(mongoAccesser);
        injector = injector.createChildInjector(new WebBinder(router, webAuth, webResult));

        // before routing request
        initWebConfig(router, webAuth, mongoAccesser);


        // scanning route
        RouteScanner scanner = injector.getInstance(RouteScanner.class);
        scanner.scan(configHolder.property(WebConst.WEB_ROUTE_INTO_PACKAGE), WebVerticle.class.getClassLoader(), injector);
    }

    /**
     * initialization web configuration.
     *
     * @param router
     * @param webAuth
     * @param mongoAccesser
     */
    private void initWebConfig(Router router, WebAuth webAuth, MongoAccesser mongoAccesser) {
        mongoAccesser.rxFindOne(MongoAccesserUtils.findOne(MongoCol.COL_SERVER, JsonResult.one().put("_id", "1")))
                .subscribe(reply -> {
                    if (JsonResult.isSucc(reply)) {
                        String accessDomain;
                        JsonObject server = (JsonObject) JsonResult.isSuccAndUnZip(reply);
                        JsonArray domains = server.getJsonArray("accessDomain");
                        if (domains == null || domains.isEmpty()) {
                            accessDomain = "*";
                        } else {
                            StringBuilder sb = new StringBuilder();
                            domains.forEach(domain -> sb.append(domain).append("|"));
                            accessDomain = sb.toString();
                            accessDomain = accessDomain.substring(0, accessDomain.length() - 1);
                        }
                        JsonArray permissions = server.getJsonArray("permissions");
                        permissions.forEach(obj -> {
                            JsonObject ele = (JsonObject) obj;
                            String role = ele.getString("role");
                            ele.getJsonArray("paths").forEach(path ->
                                    router.route((String) path).handler(webAuth.createAuthHandler(role)));
                        });
                        beforeRoutingRequest(router, accessDomain);
                    } else beforeRoutingRequest(router, "*");
                }, err -> log.info("{}loading server into failure!", MODULE_NAME));
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
