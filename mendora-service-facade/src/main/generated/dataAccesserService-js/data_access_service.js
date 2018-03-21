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

/** @module dataAccesserService-js/data_access_service */
var utils = require('vertx-js/util/utils');
var Vertx = require('vertx-js/vertx');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JDataAccessService = Java.type('org.mendora.service.dataAccesser.DataAccessService');

/**
 created by:xmf
 date:2017/10/31
 description:

 @class
*/
var DataAccessService = function(j_val) {

  var j_dataAccessService = j_val;
  var that = this;

  /**

   @public

   */
  this.register = function() {
    var __args = arguments;
    if (__args.length === 0) {
      j_dataAccessService["register()"]();
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param sql {string} 
   @param handler {function} 
   @return {DataAccessService}
   */
  this.query = function(sql, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_dataAccessService["query(java.lang.String,io.vertx.core.Handler)"](sql, function(ar) {
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
   @return {DataAccessService}
   */
  this.queryWithParams = function(doc, handler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_dataAccessService["queryWithParams(io.vertx.core.json.JsonObject,io.vertx.core.Handler)"](utils.convParamJsonObject(doc), function(ar) {
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
   @return {DataAccessService}
   */
  this.querySingle = function(sql, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_dataAccessService["querySingle(java.lang.String,io.vertx.core.Handler)"](sql, function(ar) {
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
   @return {DataAccessService}
   */
  this.querySingleWithParams = function(doc, handler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_dataAccessService["querySingleWithParams(io.vertx.core.json.JsonObject,io.vertx.core.Handler)"](utils.convParamJsonObject(doc), function(ar) {
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
   @return {DataAccessService}
   */
  this.update = function(sql, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_dataAccessService["update(java.lang.String,io.vertx.core.Handler)"](sql, function(ar) {
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
   @return {DataAccessService}
   */
  this.updateWithParams = function(doc, handler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_dataAccessService["updateWithParams(io.vertx.core.json.JsonObject,io.vertx.core.Handler)"](utils.convParamJsonObject(doc), function(ar) {
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
   @return {DataAccessService}
   */
  this.execute = function(sql, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_dataAccessService["execute(java.lang.String,io.vertx.core.Handler)"](sql, function(ar) {
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
  this._jdel = j_dataAccessService;
};

DataAccessService._jclass = utils.getJavaClass("org.mendora.service.dataAccesser.DataAccessService");
DataAccessService._jtype = {
  accept: function(obj) {
    return DataAccessService._jclass.isInstance(obj._jdel);
  },
  wrap: function(jdel) {
    var obj = Object.create(DataAccessService.prototype, {});
    DataAccessService.apply(obj, arguments);
    return obj;
  },
  unwrap: function(obj) {
    return obj._jdel;
  }
};
DataAccessService._create = function(jdel) {
  var obj = Object.create(DataAccessService.prototype, {});
  DataAccessService.apply(obj, arguments);
  return obj;
}
/**
 create service proxy.

 @memberof module:dataAccesserService-js/data_access_service
 @param vertx {Vertx} 
 @return {DataAccessService} 
 */
DataAccessService.createProxy = function(vertx) {
  var __args = arguments;
  if (__args.length === 1 && typeof __args[0] === 'object' && __args[0]._jdel) {
    return utils.convReturnVertxGen(DataAccessService, JDataAccessService["createProxy(io.vertx.core.Vertx)"](vertx._jdel));
  } else throw new TypeError('function invoked with invalid arguments');
};

module.exports = DataAccessService;