package org.mendora.data.verticles;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.mendora.base.verticles.SimpleVerticle;
import org.mendora.data.accesser.DataAccesser;
import org.mendora.util.constant.DataAddress;

import java.util.List;

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
            List<JsonObject> rows = (List<JsonObject>) DataAccesser.rxQuery(doc.getString("statement"));
            msg.reply(new JsonArray(rows));
        });
    }
}
