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

/** @module dataAccesserService-js/postgre_accesser */
var utils = require('vertx-js/util/utils');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JPostgreAccesser = Java.type('org.mendora.service.facade.dataAccesser.PostgreAccesser');

/**
 created by:xmf
 date:2017/10/31
 description:

 @class
*/
var PostgreAccesser = function(j_val) {

  var j_postgreAccesser = j_val;
  var that = this;

  /**

   @public
   @param handler {function} 
   @return {PostgreAccesser}
   */
  this.unRegister = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_postgreAccesser["unRegister(io.vertx.core.Handler)"](function(ar) {
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
   @return {PostgreAccesser}
   */
  this.pause = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_postgreAccesser["pause(io.vertx.core.Handler)"](function(ar) {
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
   @return {PostgreAccesser}
   */
  this.resume = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_postgreAccesser["resume(io.vertx.core.Handler)"](function(ar) {
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
   @return {PostgreAccesser}
   */
  this.isRegistered = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_postgreAccesser["isRegistered(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param sql {string} 
   @param handler {function} 
   @return {PostgreAccesser}
   */
  this.query = function(sql, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_postgreAccesser["query(java.lang.String,io.vertx.core.Handler)"](sql, function(ar) {
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
   @param doc {Object} 
   @param handler {function} 
   @return {PostgreAccesser}
   */
  this.queryWithParams = function(doc, handler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_postgreAccesser["queryWithParams(io.vertx.core.json.JsonObject,io.vertx.core.Handler)"](utils.convParamJsonObject(doc), function(ar) {
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
   @param sql {string} 
   @param handler {function} 
   @return {PostgreAccesser}
   */
  this.querySingle = function(sql, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_postgreAccesser["querySingle(java.lang.String,io.vertx.core.Handler)"](sql, function(ar) {
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
   @param doc {Object} 
   @param handler {function} 
   @return {PostgreAccesser}
   */
  this.querySingleWithParams = function(doc, handler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_postgreAccesser["querySingleWithParams(io.vertx.core.json.JsonObject,io.vertx.core.Handler)"](utils.convParamJsonObject(doc), function(ar) {
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
   @param sql {string} 
   @param handler {function} 
   @return {PostgreAccesser}
   */
  this.update = function(sql, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_postgreAccesser["update(java.lang.String,io.vertx.core.Handler)"](sql, function(ar) {
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
   @param doc {Object} 
   @param handler {function} 
   @return {PostgreAccesser}
   */
  this.updateWithParams = function(doc, handler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_postgreAccesser["updateWithParams(io.vertx.core.json.JsonObject,io.vertx.core.Handler)"](utils.convParamJsonObject(doc), function(ar) {
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
   @param sql {string} 
   @param handler {function} 
   @return {PostgreAccesser}
   */
  this.execute = function(sql, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_postgreAccesser["execute(java.lang.String,io.vertx.core.Handler)"](sql, function(ar) {
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
  this._jdel = j_postgreAccesser;
};

PostgreAccesser._jclass = utils.getJavaClass("org.mendora.service.facade.dataAccesser.PostgreAccesser");
PostgreAccesser._jtype = {
  accept: function(obj) {
    return PostgreAccesser._jclass.isInstance(obj._jdel);
  },
  wrap: function(jdel) {
    var obj = Object.create(PostgreAccesser.prototype, {});
    PostgreAccesser.apply(obj, arguments);
    return obj;
  },
  unwrap: function(obj) {
    return obj._jdel;
  }
};
PostgreAccesser._create = function(jdel) {
  var obj = Object.create(PostgreAccesser.prototype, {});
  PostgreAccesser.apply(obj, arguments);
  return obj;
}
module.exports = PostgreAccesser;