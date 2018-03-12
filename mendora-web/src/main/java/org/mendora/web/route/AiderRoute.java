package org.mendora.web.route;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.rxjava.core.eventbus.Message;
import org.mendora.base.utils.VertxHolder;
import org.mendora.util.constant.DataAddress;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
public class AiderRoute implements Route {
    @Override
    public void route(Router router) {
        router.get("/mendora/aider/demo").handler(rc -> {
            rc.response().end("<h1>Just a test demo.</h1>");
        });
        router.post("/mendora/aider/data/sonar").handler(rc -> {
            JsonObject doc = rc.getBodyAsJson();
            VertxHolder.eventBus().<JsonObject>rxSend(DataAddress.DATA_EB_COMMON_SONAR, doc)
                    .map(Message::body)
                    .subscribe(replyJson -> rc.response().end(Json.encode(replyJson)));
        });
    }
}
