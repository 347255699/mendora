package org.mendora.web.route;

import com.google.inject.Inject;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.rxjava.core.eventbus.Message;
import lombok.extern.slf4j.Slf4j;
import org.mendora.base.properties.ConfigHolder;
import org.mendora.base.utils.VertxHolder;
import org.mendora.service.dataAccesser.rxjava.DataAccessService;
import org.mendora.util.constant.EBAddress;
import org.mendora.util.constant.SqlReferences;
import org.mendora.util.constant.WebResult;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
@Slf4j
public class AiderRoute implements Route {
    private static final String MODULE = "/mendora/aider";
    private static final String SQL_STATIEMENT = MODULE + "/sqlStatement";
    @Inject
    private DataAccessService dataAccessService;

    @Override
    public void route(Router router) {
        // route demo
        router.get(MODULE + "/demo").handler(rc -> {
            rc.response().end("<h1>Just a test demo.</h1>");
        });

        // rpc service demo
        router.post(SQL_STATIEMENT + "/query").handler(rc -> {
            dataAccessService.rxQuery(rc.getBodyAsJson().getString(SqlReferences.STATEMENT.val()))
                    .subscribe(replyJson -> WebResult.consume(replyJson, rc));
        });

        /** eventbus demo **/
        router.post(SQL_STATIEMENT + "/queryWithParams").handler(rc -> {
            JsonObject doc = rc.getBodyAsJson();
            VertxHolder.eventBus().<JsonObject>rxSend(EBAddress.DATA_EB_QUERY_WITH_PARAMS, doc)
                    .map(Message::body)
                    .subscribe(replyJson -> WebResult.consume(replyJson, rc));
        });

        router.post(SQL_STATIEMENT + "/querySingle").handler(rc -> {
            String sqlStatement = rc.getBodyAsJson().getString(SqlReferences.STATEMENT.val());
            VertxHolder.eventBus().<JsonObject>rxSend(EBAddress.DATA_EB_QUERY_SINGLE, sqlStatement)
                    .map(Message::body)
                    .subscribe(replyJson -> WebResult.consume(replyJson, rc));
        });

        router.post(SQL_STATIEMENT + "/querySingleWithParams").handler(rc -> {
            JsonObject doc = rc.getBodyAsJson();
            VertxHolder.eventBus().<JsonObject>rxSend(EBAddress.DATA_EB_QUERY_SINGLE_WITH_PARAMS, doc)
                    .map(Message::body)
                    .subscribe(replyJson -> WebResult.consume(replyJson, rc));
        });

        router.post(SQL_STATIEMENT + "/update").handler(rc -> {
            String sqlStatement = rc.getBodyAsJson().getString(SqlReferences.STATEMENT.val());
            VertxHolder.eventBus().<JsonObject>rxSend(EBAddress.DATA_EB_UPDATE, sqlStatement)
                    .map(Message::body)
                    .subscribe(replyJson -> WebResult.consume(replyJson, rc));
        });

        router.post(SQL_STATIEMENT + "/updateWithParams").handler(rc -> {
            JsonObject doc = rc.getBodyAsJson();
            VertxHolder.eventBus().<JsonObject>rxSend(EBAddress.DATA_EB_UPDATE_WITH_PARAMS, doc)
                    .map(Message::body)
                    .subscribe(replyJson -> WebResult.consume(replyJson, rc));
        });

        router.post(SQL_STATIEMENT + "/batchWithParams").handler(rc -> {
            JsonObject doc = rc.getBodyAsJson();
            VertxHolder.eventBus().<JsonObject>rxSend(EBAddress.DATA_EB_BATCH_WITH_PARAMS, doc)
                    .map(Message::body)
                    .subscribe(replyJson -> WebResult.consume(replyJson, rc));
        });

        router.post(SQL_STATIEMENT + "/execute").handler(rc -> {
            String sqlStatement = rc.getBodyAsJson().getString(SqlReferences.STATEMENT.val());
            VertxHolder.eventBus().<JsonObject>rxSend(EBAddress.DATA_EB_EXECUTE, sqlStatement)
                    .map(Message::body)
                    .subscribe(replyJson -> WebResult.consume(replyJson, rc));
        });

        // get configuration properties list.
        router.get(MODULE + "/config").handler(rc -> {
            WebResult.succ(ConfigHolder.asJson(), rc);
        });
    }
}
