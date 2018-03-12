package org.mendora.data.verticles;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;
import org.mendora.base.verticles.SimpleVerticle;
import org.mendora.util.constant.DataAddress;

/**
 * created by:xmf
 * date:2018/3/12
 * description:
 */
public class DataVerticle extends SimpleVerticle {
    @Override
    public DeploymentOptions options() {
        return super.options();
    }

    @Override
    public void start() throws Exception {
        // common query interface
        vertx.eventBus().<JsonObject>consumer(DataAddress.DATA_EB_COMMON_QUERY).handler(msg -> {
            JsonObject doc = msg.body();
            // msg.reply(doc);
        });
    }
}
