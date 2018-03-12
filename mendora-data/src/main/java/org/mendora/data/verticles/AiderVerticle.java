package org.mendora.data.verticles;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;
import org.mendora.base.verticles.SimpleVerticle;
import org.mendora.data.client.ClientHolder;
import org.mendora.util.constant.DataAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * created by:xmf
 * date:2018/3/12
 * description:
 */
public class AiderVerticle extends SimpleVerticle {
    private Logger logger = LoggerFactory.getLogger(SimpleVerticle.class);

    @Override
    public DeploymentOptions options() {
        return super.options();
    }

    @Override
    public void start() throws Exception {
        // 声呐检测
        vertx.eventBus().<JsonObject>consumer(DataAddress.DATA_EB_COMMON_SONAR).handler(msg -> {
            JsonObject doc = msg.body();
            logger.info(doc.toString());
            String table = doc.getString("table");
            //ClientHolder.postgre()
            msg.reply(doc);
        });
    }
}
