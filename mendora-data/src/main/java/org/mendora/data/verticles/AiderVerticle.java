package org.mendora.data.verticles;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;
import org.mendora.base.verticles.SimpleVerticle;
import org.mendora.data.service.ebService.DataAccesser;
import org.mendora.util.constant.EBAddress;
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
        /** testing **/
        vertx.eventBus().<String>consumer(EBAddress.DATA_EB_QUERY).handler(DataAccesser::query);

        vertx.eventBus().<JsonObject>consumer(EBAddress.DATA_EB_QUERY_WITH_PARAMS).handler(DataAccesser::queryWithParams);

        vertx.eventBus().<String>consumer(EBAddress.DATA_EB_QUERY_SINGLE).handler(DataAccesser::querySingle);

        vertx.eventBus().<JsonObject>consumer(EBAddress.DATA_EB_QUERY_SINGLE_WITH_PARAMS).handler(DataAccesser::querySingleWithParams);

        vertx.eventBus().<String>consumer(EBAddress.DATA_EB_UPDATE).handler(DataAccesser::update);

        vertx.eventBus().<JsonObject>consumer(EBAddress.DATA_EB_UPDATE_WITH_PARAMS).handler(DataAccesser::updateWithParams);

        vertx.eventBus().<String>consumer(EBAddress.DATA_EB_EXECUTE).handler(DataAccesser::execute);
    }
}
