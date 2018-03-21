package org.mendora.util.result;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.http.HttpServerResponse;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.mendora.util.constant.SysConst;

/**
 * created by:xmf
 * date:2018/3/13
 * description:response result set.
 */
public class WebResult {

    /**
     * only success status code.
     * example:{
     * "retCode":0
     * }
     */
    public static void succ(RoutingContext rc) {
        common(JsonResult.succ(), rc);
    }

    /**
     * return sucess status code and payload.
     * example:{
     * "retCode":0,
     * "data":[payload]
     * }
     */
    public static void succ(JsonObject payload, RoutingContext rc) {
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
    public static void succ(JsonArray rows, RoutingContext rc) {
        JsonObject payload = JsonResult.allocate(2)
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
    public static void fail(RoutingContext rc) {
        common(JsonResult.fail(), rc);
    }

    /**
     * return failure status code and error msg.
     * example:{
     * "retCode":-1,
     * "errMsg":[err.getMessage()]
     * }
     */
    public static void fail(Throwable err, RoutingContext rc) {
        common(JsonResult.fail(err), rc);
    }

    /**
     * only half success status code.
     * example:{
     * "retCode":1
     * }
     */
    public static void halfSucc(RoutingContext rc) {
        common(JsonResult.halfSucc(), rc);
    }

    /**
     * return half success status code and payload.
     * example:{
     * "retCode":1,
     * "data":[payload]
     * }
     */
    public static void halfSucc(JsonObject payload, RoutingContext rc) {
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
    public static void halfSucc(JsonArray rows, RoutingContext rc) {
        JsonObject payload = JsonResult.allocate(2)
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
    public static void consume(JsonObject payload, RoutingContext rc) {
        common(payload, rc);
    }

    /**
     * customize your response payload and default status code
     *
     * @param payload
     * @param rc
     */
    public static void consume(JsonObject payload, int defaultRetCode, RoutingContext rc) {
        if (!payload.containsKey(SysConst.SYS_RET_CODE))
            payload.put(SysConst.SYS_RET_CODE, defaultRetCode);
        common(payload, rc);
    }

    /**
     * customize your response payload and default status code
     *
     * @param result
     */
    public static void consume(JsonObject header, String result, RoutingContext rc) {
        HttpServerResponse response = rc.response();
        header.fieldNames().forEach(k -> response.putHeader(k, header.getString(k)));
        response.end(result);
    }

    /**
     * final response method.
     *
     * @param payload
     * @param rc
     */
    private static void common(JsonObject payload, RoutingContext rc) {
        rc.response().end(payload.encode());
    }

}
