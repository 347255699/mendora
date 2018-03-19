package org.mendora.service.dataAccesser;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHelper;

/**
 * created by:xmf
 * date:2017/10/31
 * description:
 */
@ProxyGen
@VertxGen
public interface DataAccessService{

    String EB_ADDRESS = "data.eb.dataAccess";

//    /**
//     * register service.
//     *
//     * @param vertx
//     */
//    static void register(Vertx vertx, DataAccessService dataAccessService) {
//        ProxyHelper.registerService(DataAccessService.class, vertx, dataAccessService, EB_ADDRESS);
//    }

    /**
     * create service proxy.
     *
     * @param vertx
     * @return
     */
    static DataAccessService createProxy(Vertx vertx) {
        return ProxyHelper.createProxy(DataAccessService.class, vertx, EB_ADDRESS);
    }

    @Fluent
    DataAccessService query(String sql, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    DataAccessService queryWithParams(JsonObject doc, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    DataAccessService querySingle(String sql, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    DataAccessService querySingleWithParams(JsonObject doc, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    DataAccessService update(String sql, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    DataAccessService updateWithParams(JsonObject doc, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    DataAccessService execute(String sql, Handler<AsyncResult<JsonObject>> handler);
}
