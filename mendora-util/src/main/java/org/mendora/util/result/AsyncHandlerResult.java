package org.mendora.util.result;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * created by:xmf
 * date:2018/3/26
 * description:
 */
public class AsyncHandlerResult {
    public static void succWithRows(JsonArray rows, Handler<AsyncResult<JsonObject>> handler) {
        handler.handle(Future.succeededFuture(JsonResult.succWithRows(rows)));
    }

    public static void succ(Object payload, Handler<AsyncResult<JsonObject>> handler) {
        handler.handle(Future.succeededFuture(JsonResult.succ(payload)));
    }

    public static void succ(Handler<AsyncResult<JsonObject>> handler) {
        handler.handle(Future.succeededFuture());
    }


    public static void fail(String failureMessage, Handler<AsyncResult<JsonObject>> handler) {
        handler.handle(Future.failedFuture(failureMessage));
    }

    public static void fail(Throwable err, Handler<AsyncResult<JsonObject>> handler) {
        handler.handle(Future.failedFuture(err));
    }
}
