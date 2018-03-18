package org.mendora.web.route;

import com.google.inject.Inject;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.eventbus.Message;
import io.vertx.rxjava.ext.web.Router;
import lombok.extern.slf4j.Slf4j;
import org.mendora.guice.properties.ConfigHolder;
import org.mendora.util.constant.EBAddress;
import org.mendora.util.constant.SqlReferences;
import org.mendora.util.result.WebResult;


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
    private Vertx vertx;
    @Inject
    private ConfigHolder configHolder;
    @Inject
    private Router router;

//    private DataAccessService dataAccessService = DataAccessService.createProxy(vertx);

    @Override
    public void route() {
        log.info("------------------------->>>");
        // route demo
        router.get(MODULE + "/demo").handler(rc -> {
            rc.response().end("<h1>Just a test demo.</h1>");
        });

        // rpc service demo
//        router.post(SQL_STATIEMENT + "/query").handler(rc -> {
//            JsonObject doc = rc.getBodyAsJson();
//            dataAccessService.rxQuery(doc.getString(SqlReferences.STATEMENT.val()))
//                    .subscribe(replyJson -> WebResult.consume(replyJson, rc));
//        });

        /** eventbus demo **/
        router.post(SQL_STATIEMENT + "/queryWithParams").handler(rc -> {
            JsonObject doc = rc.getBodyAsJson();
            vertx.eventBus().<JsonObject>rxSend(EBAddress.DATA_EB_QUERY_WITH_PARAMS, doc)
                    .map(Message::body)
                    .subscribe(replyJson -> WebResult.consume(replyJson, rc));
        });

        router.post(SQL_STATIEMENT + "/querySingle").handler(rc -> {
            String sqlStatement = rc.getBodyAsJson().getString(SqlReferences.STATEMENT.val());
            vertx.eventBus().<JsonObject>rxSend(EBAddress.DATA_EB_QUERY_SINGLE, sqlStatement)
                    .map(Message::body)
                    .subscribe(replyJson -> WebResult.consume(replyJson, rc));
        });

        router.post(SQL_STATIEMENT + "/querySingleWithParams").handler(rc -> {
            JsonObject doc = rc.getBodyAsJson();
            vertx.eventBus().<JsonObject>rxSend(EBAddress.DATA_EB_QUERY_SINGLE_WITH_PARAMS, doc)
                    .map(Message::body)
                    .subscribe(replyJson -> WebResult.consume(replyJson, rc));
        });

        router.post(SQL_STATIEMENT + "/update").handler(rc -> {
            String sqlStatement = rc.getBodyAsJson().getString(SqlReferences.STATEMENT.val());
            vertx.eventBus().<JsonObject>rxSend(EBAddress.DATA_EB_UPDATE, sqlStatement)
                    .map(Message::body)
                    .subscribe(replyJson -> WebResult.consume(replyJson, rc));
        });

        router.post(SQL_STATIEMENT + "/updateWithParams").handler(rc -> {
            JsonObject doc = rc.getBodyAsJson();
            vertx.eventBus().<JsonObject>rxSend(EBAddress.DATA_EB_UPDATE_WITH_PARAMS, doc)
                    .map(Message::body)
                    .subscribe(replyJson -> WebResult.consume(replyJson, rc));
        });

        router.post(SQL_STATIEMENT + "/batchWithParams").handler(rc -> {
            JsonObject doc = rc.getBodyAsJson();
            vertx.eventBus().<JsonObject>rxSend(EBAddress.DATA_EB_BATCH_WITH_PARAMS, doc)
                    .map(Message::body)
                    .subscribe(replyJson -> WebResult.consume(replyJson, rc));
        });

        router.post(SQL_STATIEMENT + "/execute").handler(rc -> {
            String sqlStatement = rc.getBodyAsJson().getString(SqlReferences.STATEMENT.val());
            vertx.eventBus().<JsonObject>rxSend(EBAddress.DATA_EB_EXECUTE, sqlStatement)
                    .map(Message::body)
                    .subscribe(replyJson -> WebResult.consume(replyJson, rc));
        });

        // get configuration properties list.
        router.get(MODULE + "/config").handler(rc -> {
            WebResult.succ(configHolder.asJson(), rc);
        });
    }

}
