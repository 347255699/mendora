package org.mendora.data.service.ebService;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

/**
 * created by:xmf
 * date:2017/10/31
 * description:
 */
@ProxyGen
public interface DataAccessService {

    // 一些用于创建服务实例和服务代理实例的工厂方法
//    static DataAccessService create(Vertx vertx) {
//       // return new SomeDatabaseServiceImpl(vertx);
//        return null;
//    }
//
//    static DataAccessService createProxy(Vertx vertx, String address) {
//        //return new SomeDatabaseServiceVertxEBProxy(vertx, address);
//        return null;
//    }
    void query(String sql, Handler<AsyncResult<JsonObject>> handler);
    void queryWithParams(JsonObject doc, Handler<AsyncResult<JsonObject>> handler);
    void querySingle(String sql, Handler<AsyncResult<JsonObject>> handler);
    void querySingleWithParams(JsonObject doc, Handler<AsyncResult<JsonObject>> handler);
    void update(String sql, Handler<AsyncResult<JsonObject>> handler);
    void updateWithParams(JsonObject doc, Handler<AsyncResult<JsonObject>> handler);
    void execute(String sql, Handler<AsyncResult<JsonObject>> handler);
}
