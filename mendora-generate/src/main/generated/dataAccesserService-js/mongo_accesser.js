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

/** @module dataAccesserService-js/mongo_accesser */
var utils = require('vertx-js/util/utils');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JMongoAccesser = Java.type('org.mendora.service.facade.dataAccesser.MongoAccesser');

/**
 Created by kam on 2018/3/26.

 @class
*/
var MongoAccesser = function(j_val) {

  var j_mongoAccesser = j_val;
  var that = this;

  /**

   @public
   @param handler {function} 
   @return {MongoAccesser}
   */
  this.unRegister = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_mongoAccesser["unRegister(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param handler {function} 
   @return {MongoAccesser}
   */
  this.pause = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_mongoAccesser["pause(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param handler {function} 
   @return {MongoAccesser}
   */
  this.resume = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_mongoAccesser["resume(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param handler {function} 
   @return {MongoAccesser}
   */
  this.isRegistered = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_mongoAccesser["isRegistered(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param params {Object} 
   @param handler {function} 
   @return {MongoAccesser}
   */
  this.save = function(params, handler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_mongoAccesser["save(io.vertx.core.json.JsonObject,io.vertx.core.Handler)"](utils.convParamJsonObject(params), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param params {Object} 
   @param handler {function} 
   @return {MongoAccesser}
   */
  this.find = function(params, handler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_mongoAccesser["find(io.vertx.core.json.JsonObject,io.vertx.core.Handler)"](utils.convParamJsonObject(params), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param params {Object} 
   @param handler {function} 
   @return {MongoAccesser}
   */
  this.findWithPage = function(params, handler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_mongoAccesser["findWithPage(io.vertx.core.json.JsonObject,io.vertx.core.Handler)"](utils.convParamJsonObject(params), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param params {Object} 
   @param handler {function} 
   @return {MongoAccesser}
   */
  this.findOne = function(params, handler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_mongoAccesser["findOne(io.vertx.core.json.JsonObject,io.vertx.core.Handler)"](utils.convParamJsonObject(params), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param params {Object} 
   @param handler {function} 
   @return {MongoAccesser}
   */
  this.remove = function(params, handler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_mongoAccesser["remove(io.vertx.core.json.JsonObject,io.vertx.core.Handler)"](utils.convParamJsonObject(params), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param params {Object} 
   @param handler {function} 
   @return {MongoAccesser}
   */
  this.count = function(params, handler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_mongoAccesser["count(io.vertx.core.json.JsonObject,io.vertx.core.Handler)"](utils.convParamJsonObject(params), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param params {Object} 
   @param handler {function} 
   @return {MongoAccesser}
   */
  this.execute = function(params, handler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_mongoAccesser["execute(io.vertx.core.json.JsonObject,io.vertx.core.Handler)"](utils.convParamJsonObject(params), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_mongoAccesser;
};

MongoAccesser._jclass = utils.getJavaClass("org.mendora.service.facade.dataAccesser.MongoAccesser");
MongoAccesser._jtype = {
  accept: function(obj) {
    return MongoAccesser._jclass.isInstance(obj._jdel);
  },
  wrap: function(jdel) {
    var obj = Object.create(MongoAccesser.prototype, {});
    MongoAccesser.apply(obj, arguments);
    return obj;
  },
  unwrap: function(obj) {
    return obj._jdel;
  }
};
MongoAccesser._create = function(jdel) {
  var obj = Object.create(MongoAccesser.prototype, {});
  MongoAccesser.apply(obj, arguments);
  return obj;
}
module.exports = MongoAccesser;