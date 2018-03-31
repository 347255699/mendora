/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package org.mendora.service.facade.dataAccesser.rxjava;

import com.google.inject.Inject;
import rx.Single;
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by kam on 2018/3/26.
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link org.mendora.service.facade.dataAccesser.MongoAccesser original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(org.mendora.service.facade.dataAccesser.MongoAccesser.class)
public class MongoAccesser {

  public static final io.vertx.lang.rxjava.TypeArg<MongoAccesser> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new MongoAccesser((org.mendora.service.facade.dataAccesser.MongoAccesser) obj),
    MongoAccesser::getDelegate
  );

  private final org.mendora.service.facade.dataAccesser.MongoAccesser delegate;

  @Inject
  public MongoAccesser(org.mendora.service.facade.dataAccesser.MongoAccesser delegate) {
    this.delegate = delegate;
  }

  public org.mendora.service.facade.dataAccesser.MongoAccesser getDelegate() {
    return delegate;
  }

  public MongoAccesser unRegister(Handler<AsyncResult<Void>> handler) { 
    delegate.unRegister(handler);
    return this;
  }

  public Single<Void> rxUnRegister() { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      unRegister(fut);
    }));
  }

  public MongoAccesser pause(Handler<AsyncResult<Void>> handler) { 
    delegate.pause(handler);
    return this;
  }

  public Single<Void> rxPause() { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      pause(fut);
    }));
  }

  public MongoAccesser resume(Handler<AsyncResult<Void>> handler) { 
    delegate.resume(handler);
    return this;
  }

  public Single<Void> rxResume() { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      resume(fut);
    }));
  }

  public MongoAccesser isRegistered(Handler<AsyncResult<JsonObject>> handler) { 
    delegate.isRegistered(handler);
    return this;
  }

  public Single<JsonObject> rxIsRegistered() { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      isRegistered(fut);
    }));
  }

  public MongoAccesser save(JsonObject params, Handler<AsyncResult<JsonObject>> handler) { 
    delegate.save(params, handler);
    return this;
  }

  public Single<JsonObject> rxSave(JsonObject params) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      save(params, fut);
    }));
  }

  public MongoAccesser find(JsonObject params, Handler<AsyncResult<JsonObject>> handler) { 
    delegate.find(params, handler);
    return this;
  }

  public Single<JsonObject> rxFind(JsonObject params) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      find(params, fut);
    }));
  }

  public MongoAccesser findWithPage(JsonObject params, Handler<AsyncResult<JsonObject>> handler) { 
    delegate.findWithPage(params, handler);
    return this;
  }

  public Single<JsonObject> rxFindWithPage(JsonObject params) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      findWithPage(params, fut);
    }));
  }

  public MongoAccesser findOne(JsonObject params, Handler<AsyncResult<JsonObject>> handler) { 
    delegate.findOne(params, handler);
    return this;
  }

  public Single<JsonObject> rxFindOne(JsonObject params) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      findOne(params, fut);
    }));
  }

  public MongoAccesser remove(JsonObject params, Handler<AsyncResult<JsonObject>> handler) { 
    delegate.remove(params, handler);
    return this;
  }

  public Single<JsonObject> rxRemove(JsonObject params) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      remove(params, fut);
    }));
  }

  public MongoAccesser count(JsonObject params, Handler<AsyncResult<JsonObject>> handler) { 
    delegate.count(params, handler);
    return this;
  }

  public Single<JsonObject> rxCount(JsonObject params) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      count(params, fut);
    }));
  }

  public MongoAccesser execute(JsonObject params, Handler<AsyncResult<JsonObject>> handler) { 
    delegate.execute(params, handler);
    return this;
  }

  public Single<JsonObject> rxExecute(JsonObject params) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      execute(params, fut);
    }));
  }


  public static  MongoAccesser newInstance(org.mendora.service.facade.dataAccesser.MongoAccesser arg) {
    return arg != null ? new MongoAccesser(arg) : null;
  }
}
