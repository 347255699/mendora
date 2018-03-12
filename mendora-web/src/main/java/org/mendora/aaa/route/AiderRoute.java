package org.mendora.aaa.route;

import io.vertx.ext.web.Router;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
public class AiderRoute implements Route {
    @Override
    public void route(Router router) {
        router.get("/mendora/demo").handler(rc -> {
            rc.response().end("<h1>Just a test demo.</h1>");
        });
    }
}
