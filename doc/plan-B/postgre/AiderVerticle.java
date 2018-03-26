package org.mendora.data.verticles;

import com.google.inject.Inject;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.mendora.data.service.ebService.DataAccesser;
import org.mendora.guice.verticles.DefaultVerticle;
import org.mendora.util.constant.EBAddress;

/**
 * created by:xmf
 * date:2018/3/12
 * description:
 */
@Slf4j
public class AiderVerticle extends DefaultVerticle {
    private static final String MODULE_NAME = "AIDER_VERTICLE:";
    @Inject
    private EventBus eb;

    @Override
    public void start() throws Exception {
        /** testing **/
        eb.<String>consumer(EBAddress.DATA_EB_QUERY).handler(DataAccesser::query);

        eb.<JsonObject>consumer(EBAddress.DATA_EB_QUERY_WITH_PARAMS).handler(DataAccesser::queryWithParams);

        eb.<String>consumer(EBAddress.DATA_EB_QUERY_SINGLE).handler(DataAccesser::querySingle);

        eb.<JsonObject>consumer(EBAddress.DATA_EB_QUERY_SINGLE_WITH_PARAMS).handler(DataAccesser::querySingleWithParams);

        eb.<String>consumer(EBAddress.DATA_EB_UPDATE).handler(DataAccesser::update);

        eb.<JsonObject>consumer(EBAddress.DATA_EB_UPDATE_WITH_PARAMS).handler(DataAccesser::updateWithParams);

        eb.<String>consumer(EBAddress.DATA_EB_EXECUTE).handler(DataAccesser::execute);
    }
}
