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

import java.util.Map;
import rx.Observable;
import rx.Single;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * created by:xmf
 * date:2017/10/31
 * description:
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link org.mendora.service.facade.dataAccesser.PostgreAccesser original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(org.mendora.service.facade.dataAccesser.PostgreAccesser.class)
public class PostgreAccesser {

  public static final io.vertx.lang.rxjava.TypeArg<PostgreAccesser> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new PostgreAccesser((org.mendora.service.facade.dataAccesser.PostgreAccesser) obj),
    PostgreAccesser::getDelegate
  );

  private final org.mendora.service.facade.dataAccesser.PostgreAccesser delegate;
  
  public PostgreAccesser(org.mendora.service.facade.dataAccesser.PostgreAccesser delegate) {
    this.delegate = delegate;
  }

  public org.mendora.service.facade.dataAccesser.PostgreAccesser getDelegate() {
    return delegate;
  }

  /**
   * create service proxy.
   * @param vertx 
   * @return 
   */
  public static PostgreAccesser createProxy(Vertx vertx) { 
    PostgreAccesser ret = PostgreAccesser.newInstance(org.mendora.service.facade.dataAccesser.PostgreAccesser.createProxy(vertx.getDelegate()));
    return ret;
  }

  public void register() { 
    delegate.register();
  }

  public PostgreAccesser query(String sql, Handler<AsyncResult<JsonObject>> handler) { 
    delegate.query(sql, handler);
    return this;
  }

  public Single<JsonObject> rxQuery(String sql) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      query(sql, fut);
    }));
  }

  public PostgreAccesser queryWithParams(JsonObject doc, Handler<AsyncResult<JsonObject>> handler) { 
    delegate.queryWithParams(doc, handler);
    return this;
  }

  public Single<JsonObject> rxQueryWithParams(JsonObject doc) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      queryWithParams(doc, fut);
    }));
  }

  public PostgreAccesser querySingle(String sql, Handler<AsyncResult<JsonObject>> handler) { 
    delegate.querySingle(sql, handler);
    return this;
  }

  public Single<JsonObject> rxQuerySingle(String sql) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      querySingle(sql, fut);
    }));
  }

  public PostgreAccesser querySingleWithParams(JsonObject doc, Handler<AsyncResult<JsonObject>> handler) { 
    delegate.querySingleWithParams(doc, handler);
    return this;
  }

  public Single<JsonObject> rxQuerySingleWithParams(JsonObject doc) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      querySingleWithParams(doc, fut);
    }));
  }

  public PostgreAccesser update(String sql, Handler<AsyncResult<JsonObject>> handler) { 
    delegate.update(sql, handler);
    return this;
  }

  public Single<JsonObject> rxUpdate(String sql) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      update(sql, fut);
    }));
  }

  public PostgreAccesser updateWithParams(JsonObject doc, Handler<AsyncResult<JsonObject>> handler) { 
    delegate.updateWithParams(doc, handler);
    return this;
  }

  public Single<JsonObject> rxUpdateWithParams(JsonObject doc) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      updateWithParams(doc, fut);
    }));
  }

  public PostgreAccesser execute(String sql, Handler<AsyncResult<JsonObject>> handler) { 
    delegate.execute(sql, handler);
    return this;
  }

  public Single<JsonObject> rxExecute(String sql) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      execute(sql, fut);
    }));
  }


  public static  PostgreAccesser newInstance(org.mendora.service.facade.dataAccesser.PostgreAccesser arg) {
    return arg != null ? new PostgreAccesser(arg) : null;
  }
}
