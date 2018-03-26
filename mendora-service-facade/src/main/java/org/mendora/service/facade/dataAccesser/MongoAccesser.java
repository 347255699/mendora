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
 * Created by kam on 2018/3/26.
 */
@ProxyGen
@VertxGen
public interface MongoAccesser {
    String EB_ADDRESS = "eb.data.mongo.accesser";

    /**
     * create service proxy.
     *
     * @param vertx
     * @return
     */
    static MongoAccesser createProxy(Vertx vertx) {
        return ProxyHelper.createProxy(MongoAccesser.class, vertx, EB_ADDRESS);
    }

    void register();

    @Fluent
    MongoAccesser save(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    MongoAccesser find(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    MongoAccesser findOne(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    MongoAccesser remove(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    MongoAccesser count(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    MongoAccesser execute(JsonObject params, Handler<AsyncResult<JsonObject>> handler);
}
