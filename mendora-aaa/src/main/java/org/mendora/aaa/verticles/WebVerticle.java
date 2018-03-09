package org.mendora.aaa.verticles;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import org.mendora.aaa.constant.AAAConst;
import org.mendora.aaa.launcher.AAALauncher;
import org.mendora.aaa.route.Route;
import org.mendora.base.properties.ConfigHolder;
import org.mendora.base.scanner.SimplePackageScanner;
import org.mendora.base.verticles.SimpleVerticle;
import rx.Observable;

import java.util.List;
import java.util.Set;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
public class WebVerticle extends SimpleVerticle {
    private static final String MODULE_NAME = "WEB-VERTICLE:";
    private static Logger logger = LoggerFactory.getLogger(AAALauncher.class);

    @Override
    public DeploymentOptions options() {
        // 设置高可用
        return super.options();
    }

    @Override
    public void start() throws Exception {
        logger.info(MODULE_NAME + "into WebVerticle");
        Router router = Router.router(vertx);
        ClassLoader currClassLoader = WebVerticle.class.getClassLoader();
        // scanning route
        List<Route> routes = new SimplePackageScanner<Route>(ConfigHolder.property(AAAConst.AAA_WEB_ROUTE_PACKAGE), currClassLoader)
                .scan(Route.class);
        logger.info(MODULE_NAME + routes.size());
        Observable.from(routes)
                .subscribe(r -> {
                            r.route(router);
                            logger.info(MODULE_NAME + r.getClass().getName());
                        },
                        err -> logger.error(MODULE_NAME + err.getMessage()),
                        () -> {
                            String webServerPort = ConfigHolder.property(AAAConst.AAA_WEB_LISTENNING_PORT);
                            logger.info(MODULE_NAME + "all the \"routes\" deployed");
                            router.getRoutes().forEach(r -> logger.info(r.getPath()));
                            vertx.createHttpServer().requestHandler(router::accept)
                                    .listen(Integer.parseInt(webServerPort));
                            logger.info(MODULE_NAME + "web server listenning at port:" + webServerPort);
                        });
    }
}
