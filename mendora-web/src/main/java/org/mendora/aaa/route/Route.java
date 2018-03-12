package org.mendora.aaa.route;


import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;

/**
 * created by:xmf
 * date:2017/10/31
 * description:
 */
public interface Route {
    Logger logger = LoggerFactory.getLogger(Route.class);
    String MODULE_NAME = "WEB-ROUTE:";

    void route(Router router);
}
