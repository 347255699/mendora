package org.mendora.service.facade.dataAccesser;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import org.mendora.service.facade.scanner.ServiceFacade;

/**
 * Created by kam on 2018/3/26.
 */
@ServiceFacade(proxy = MongoAccesserVertxEBProxy.class, rxProxy = org.mendora.service.facade.dataAccesser.rxjava.MongoAccesser.class)
public interface MongoAccesser {
    String EB_ADDRESS = "eb.data.mongo.accesser";

    @Fluent
    MongoAccesser unRegister(Handler<AsyncResult<Void>> handler);

    @Fluent
    MongoAccesser pause(Handler<AsyncResult<Void>> handler);

    @Fluent
    MongoAccesser resume(Handler<AsyncResult<Void>> handler);

    @Fluent
    MongoAccesser isRegistered(Handler<AsyncResult<JsonObject>> handler);

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
