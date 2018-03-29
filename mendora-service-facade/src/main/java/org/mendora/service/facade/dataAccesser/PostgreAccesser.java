package org.mendora.service.facade.dataAccesser;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import org.mendora.service.facade.scanner.ServiceFacade;

/**
 * created by:xmf
 * date:2017/10/31
 * description:
 */
@ProxyGen
@VertxGen
@ServiceFacade(proxy = PostgreAccesserVertxEBProxy.class, rxProxy = org.mendora.service.facade.dataAccesser.rxjava.PostgreAccesser.class)
public interface PostgreAccesser {

    String EB_ADDRESS = "eb.data.postgre.accesser";

    //    /**
//     * create service proxy.
//     *
//     * @param vertx
//     * @return
//     */
//    static PostgreAccesser createProxy(Vertx vertx) {
//        return ProxyHelper.createProxy(PostgreAccesser.class, vertx, EB_ADDRESS);
//    }
    @Fluent
    PostgreAccesser unRegister(Handler<AsyncResult<Void>> handler);

    @Fluent
    PostgreAccesser pause(Handler<AsyncResult<Void>> handler);

    @Fluent
    PostgreAccesser resume(Handler<AsyncResult<Void>> handler);

    @Fluent
    PostgreAccesser isRegistered(Handler<AsyncResult<Boolean>> handler);

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
