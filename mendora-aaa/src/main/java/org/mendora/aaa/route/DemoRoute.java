package org.mendora.aaa.route;

import io.vertx.ext.web.Router;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
public class DemoRoute implements Route {
    @Override
    public void route(Router router) {
        router.get("/demo").handler(rc -> {
           rc.response().end("Hello,boy!");
        });
    }
}
