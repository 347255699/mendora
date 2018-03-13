package org.mendora.data.verticles;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonArray;
import org.mendora.base.verticles.SimpleVerticle;
import org.mendora.data.accesser.DataAccesser;
import org.mendora.util.constant.EBAddress;
import org.mendora.util.result.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * created by:xmf
 * date:2018/3/12
 * description:
 */
public class AiderVerticle extends SimpleVerticle {
    private static final String MODULE_NAME = "AIDER_VERTICLE:";
    private Logger logger = LoggerFactory.getLogger(AiderVerticle.class);

    @Override
    public DeploymentOptions options() {
        return super.options();
    }

    @Override
    public void start() throws Exception {
        // checking data module working status.
        vertx.eventBus().<String>consumer(EBAddress.DATA_EB_COMMON_SONAR).handler(msg -> {
            String sql = msg.body();
            DataAccesser.rxQuery(sql)
                    .subscribe(rows -> msg.reply(JsonResult.succWithRows(new JsonArray(rows)))
                            , err -> msg.reply(JsonResult.fail(err)));
        });
    }
}
