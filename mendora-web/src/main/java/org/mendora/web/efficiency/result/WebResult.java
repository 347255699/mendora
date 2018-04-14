package org.mendora.web.efficiency.result;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.http.HttpServerResponse;
import io.vertx.rxjava.ext.web.RoutingContext;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mendora.service.facade.dataAccesser.mongo.rxjava.MongoAccesser;
import org.mendora.util.constant.MongoCol;
import org.mendora.util.constant.RetCode;
import org.mendora.util.constant.SysConst;
import org.mendora.util.generate.MongoAccesserUtils;
import org.mendora.util.result.JsonResult;
import org.mendora.web.constant.WebConst;

/**
 * created by:xmf
 * date:2018/3/13
 * description:response result set.
 */
@Slf4j
@RequiredArgsConstructor
public class WebResult {
    private static final String MUDULE_NAME = "WEB_RESULT:";

    @NonNull
    private MongoAccesser mongoAccesser;

    /**
     * only success status code.
     * example:{
     * "retCode":0
     * }
     */
    public void succ(RoutingContext rc) {
        common(JsonResult.succ(), rc);
    }

    /**
     * return sucess status code and payload.
     * example:{
     * "retCode":0,
     * "data":[payload]
     * }
     */
    public void succ(JsonObject payload, RoutingContext rc) {
        common(JsonResult.succ(payload), rc);
    }

    /**
     * return sucess status code and payload of json array format.
     * example:{
     * "retCode":0,
     * "data":{
     * "size":[list size],
     * "rows":[rows]
     * }
     * }
     */
    public void succ(JsonArray rows, RoutingContext rc) {
        JsonObject payload = JsonResult.two()
                .put(SysConst.SYS_ROWS, rows)
                .put(SysConst.SYS_SIZE, rows.size());
        common(JsonResult.succ(payload), rc);
    }

    /**
     * only failure status code.
     * example:{
     * "retCode":-1
     * }
     */
    public void fail(RoutingContext rc) {
        common(JsonResult.fail(), rc);
    }

    /**
     * return failure status code and error msg.
     * example:{
     * "retCode":-1,
     * "errMsg":[err.getMessage()]
     * }
     */
    public void fail(Throwable err, RoutingContext rc) {
        common(JsonResult.fail(err), rc);
    }


    /**
     * return failure status code and error msg.
     * example:{
     * "retCode":-1,
     * "errMsg":[errMsg]
     * }
     */
    public void fail(String errMsg, RoutingContext rc) {
        fail(new RuntimeException(errMsg), rc);
    }

    /**
     * only half success status code.
     * example:{
     * "retCode":1
     * }
     */
    public void halfSucc(RoutingContext rc) {
        common(JsonResult.halfSucc(), rc);
    }

    /**
     * return half success status code and payload.
     * example:{
     * "retCode":1,
     * "data":[payload]
     * }
     */
    public void halfSucc(JsonObject payload, RoutingContext rc) {
        common(JsonResult.halfSucc(payload), rc);
    }

    /**
     * return half success status code and payload of json array format.
     * example:{
     * "retCode":1,
     * "data":{
     * "size":[list size],
     * "rows":[rows]
     * }
     * }
     */
    public void halfSucc(JsonArray rows, RoutingContext rc) {
        JsonObject payload = JsonResult.two()
                .put(SysConst.SYS_ROWS, rows)
                .put(SysConst.SYS_SIZE, rows.size());
        common(JsonResult.halfSucc(payload), rc);
    }

    /**
     * customize your response payload event status code.
     *
     * @param payload
     * @param rc
     */
    public void consume(JsonObject payload, RoutingContext rc) {
        common(payload, rc);
    }

    /**
     * customize your response payload and default status code
     *
     * @param payload
     * @param rc
     */
    public void consume(JsonObject payload, int defaultRetCode, RoutingContext rc) {
        if (!payload.containsKey(SysConst.SYS_RET_CODE))
            payload.put(SysConst.SYS_RET_CODE, defaultRetCode);
        common(payload, rc);
    }

    /**
     * customize your response payload and default status code
     *
     * @param result
     */
    public void consume(JsonObject header, String result, RoutingContext rc) {
        HttpServerResponse response = rc.response();
        header.fieldNames().forEach(k -> response.putHeader(k, header.getString(k)));
        response.end(result);
        logging(JsonResult.one().put(SysConst.SYS_RET_CODE, RetCode.SUCCESS.val()), rc);
    }

    /**
     * final response method.
     *
     * @param payload
     * @param rc
     */
    private void common(JsonObject payload, RoutingContext rc) {
        rc.response().end(payload.encode());
        logging(payload, rc);
    }

    /**
     * logging http api invoke status.
     *
     * @param payload
     * @param rc
     */
    private void logging(JsonObject payload, RoutingContext rc) {
        JsonObject logging = rc.get(WebConst.WEB_ROUTE_lOGGING_KEY);
        if (rc.user() != null && logging != null && logging.size() > 0) {
            int retCode = payload.getInteger(SysConst.SYS_RET_CODE);
            logging.put(SysConst.SYS_RET_CODE, retCode);
            if (payload.containsKey(SysConst.SYS_DATA))
                logging.put(SysConst.SYS_DATA, payload.getValue(SysConst.SYS_DATA));
            if (payload.containsKey(SysConst.SYS_ERR_MSG))
                logging.put(SysConst.SYS_ERR_MSG, payload.getValue(SysConst.SYS_ERR_MSG));
            mongoAccesser.rxSave(MongoAccesserUtils.save(MongoCol.COL_LOG, logging))
                    .subscribe();
        }
    }

}
