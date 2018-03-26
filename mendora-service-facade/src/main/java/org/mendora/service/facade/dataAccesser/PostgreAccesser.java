package org.mendora.service.facade.dataAccesser;

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
public interface PostgreAccesser {

    String EB_ADDRESS = "eb.data.postgre.accesser";

    /**
     * create service proxy.
     *
     * @param vertx
     * @return
     */
    static PostgreAccesser createProxy(Vertx vertx) {
        return ProxyHelper.createProxy(PostgreAccesser.class, vertx, EB_ADDRESS);
    }

    void register();

    @Fluent
    PostgreAccesser query(String sql, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    PostgreAccesser queryWithParams(JsonObject doc, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    PostgreAccesser querySingle(String sql, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    PostgreAccesser querySingleWithParams(JsonObject doc, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    PostgreAccesser update(String sql, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    PostgreAccesser updateWithParams(JsonObject doc, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    PostgreAccesser execute(String sql, Handler<AsyncResult<JsonObject>> handler);
}
