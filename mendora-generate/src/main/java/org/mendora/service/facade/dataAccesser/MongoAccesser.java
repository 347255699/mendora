package org.mendora.service.facade.dataAccesser;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

/**
 * Created by kam on 2018/3/26.
 */
@ProxyGen
@VertxGen
public interface MongoAccesser {
    String EB_ADDRESS = "eb.data.mongo.accesser";

    @Fluent
    MongoAccesser save(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    MongoAccesser find(JsonObject params, Handler<AsyncResult<JsonObject>> handler);
    @Fluent
    MongoAccesser findWithPage(JsonObject params, Handler<AsyncResult<JsonObject>> handler);
    @Fluent
    MongoAccesser findOne(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    MongoAccesser remove(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    MongoAccesser count(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    MongoAccesser execute(JsonObject params, Handler<AsyncResult<JsonObject>> handler);
}
