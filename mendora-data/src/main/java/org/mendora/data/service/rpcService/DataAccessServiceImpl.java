package org.mendora.data.service.rpcService;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.UpdateResult;
import io.vertx.rxjava.ext.asyncsql.AsyncSQLClient;
import io.vertx.rxjava.ext.sql.SQLConnection;
import io.vertx.serviceproxy.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.mendora.data.client.ClientHolder;
import org.mendora.data.service.ebService.DataAccesser;
import org.mendora.service.dataAccesser.DataAccessService;
import org.mendora.util.constant.RetCode;
import org.mendora.util.constant.SqlReferences;
import org.mendora.util.result.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * created by:xmf
 * date:2018/3/14
 * description:
 */
@Slf4j
public class DataAccessServiceImpl implements DataAccessService {
    private static final int CLIENT_NO_READY = -2;
    private static final int POSTGRE_CONNECT_FAIL = -3;

    /**
     * get postgreSQL client
     *
     * @param handler
     * @return
     */
    private AsyncSQLClient postgreClient(Handler<AsyncResult<JsonObject>> handler) {
        if (ClientHolder.postgre() == null)
            fail(handler, CLIENT_NO_READY, "the postgre client not ready, please wait a moment and try again.");
        return ClientHolder.postgre();
    }

    /**
     * reply failure message.
     *
     * @param handler
     * @param failureCode
     * @param message
     */
    private void fail(Handler<AsyncResult<JsonObject>> handler, int failureCode, String message) {
        handler.handle(ServiceException.fail(failureCode, message));
    }

    /**
     * connecting postgreSQL db failure.
     *
     * @param handler
     */
    private void connectFailure(Handler<AsyncResult<JsonObject>> handler) {
        fail(handler, POSTGRE_CONNECT_FAIL, "postgreSQL db connect failure.");
    }

    /**
     * execute find sql statement without params
     *
     * @param sql
     * @param handler
     */
    @Override
    public DataAccessService query(String sql, Handler<AsyncResult<JsonObject>> handler) {
        postgreClient(handler).getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection conn = res.result();
                conn.rxQuery(sql)
                        .map(ResultSet::getRows)
                        .map(JsonArray::new)
                        .subscribe(rows -> {
                            conn.close();
                            handler.handle(Future.succeededFuture(JsonResult.succWithRows(rows)));
                        }, err -> fail(handler, RetCode.FAILURE.val(), err.getMessage()));
            } else {
                connectFailure(handler);
            }
        });
        return this;
    }

    /**
     * execute find sql statement with params
     *
     * @param doc
     * @param handler
     */
    @Override
    public DataAccessService queryWithParams(JsonObject doc, Handler<AsyncResult<JsonObject>> handler) {
        String sql = doc.getString(SqlReferences.STATEMENT.val());
        JsonArray params = doc.getJsonArray(SqlReferences.PARAMS.val());
        postgreClient(handler).getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection conn = res.result();
                conn.rxQueryWithParams(sql, params)
                        .map(ResultSet::getRows)
                        .map(JsonArray::new)
                        .subscribe(rows -> {
                            conn.close();
                            handler.handle(Future.succeededFuture(JsonResult.succWithRows(rows)));
                        }, err -> fail(handler, RetCode.FAILURE.val(), err.getMessage()));
            } else {
                connectFailure(handler);
            }
        });
        return this;
    }

    /**
     * find one record without params.
     *
     * @param sql
     * @param handler
     */
    @Override
    public DataAccessService querySingle(String sql, Handler<AsyncResult<JsonObject>> handler) {
        postgreClient(handler).getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection conn = res.result();
                conn.rxQuery(sql)
                        .map(ResultSet::getRows)
                        .map(JsonArray::new)
                        .subscribe(rows -> {
                            conn.close();
                            handler.handle(Future.succeededFuture(JsonResult.succ(rows.getJsonObject(0))));
                        }, err -> fail(handler, RetCode.FAILURE.val(), err.getMessage()));
            } else {
                connectFailure(handler);
            }
        });
        return this;
    }

    /**
     * find one record with params.
     *
     * @param doc
     * @param handler
     */
    @Override
    public DataAccessService querySingleWithParams(JsonObject doc, Handler<AsyncResult<JsonObject>> handler) {
        String sql = doc.getString(SqlReferences.STATEMENT.val());
        JsonArray params = doc.getJsonArray(SqlReferences.PARAMS.val());
        postgreClient(handler).getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection conn = res.result();
                conn.rxQueryWithParams(sql, params)
                        .map(ResultSet::getRows)
                        .map(JsonArray::new)
                        .subscribe(rows -> {
                            conn.close();
                            handler.handle(Future.succeededFuture(JsonResult.succ(rows.getJsonObject(0))));
                        }, err -> fail(handler, RetCode.FAILURE.val(), err.getMessage()));
            } else {
                connectFailure(handler);
            }
        });
        return this;
    }

    /**
     * executing update/insert/delete sql statement without params
     *
     * @param sql
     * @param handler
     */
    @Override
    public DataAccessService update(String sql, Handler<AsyncResult<JsonObject>> handler) {
        postgreClient(handler).getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection conn = res.result();
                conn.rxUpdate(sql)
                        .map(UpdateResult::toJson)
                        .subscribe(updateResult -> {
                            conn.close();
                            handler.handle(Future.succeededFuture(JsonResult.succ(updateResult)));
                        }, err -> fail(handler, RetCode.FAILURE.val(), err.getMessage()));
            } else {
                connectFailure(handler);
            }
        });
        return this;
    }

    /**
     * execute update/insert/delete statement with params
     *
     * @param doc
     * @param handler
     */
    @Override
    public DataAccessService updateWithParams(JsonObject doc, Handler<AsyncResult<JsonObject>> handler) {
        String sql = doc.getString(SqlReferences.STATEMENT.val());
        JsonArray params = doc.getJsonArray(SqlReferences.PARAMS.val());
        postgreClient(handler).getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection conn = res.result();
                conn.rxUpdateWithParams(sql, params)
                        .map(UpdateResult::toJson)
                        .subscribe(updateResult -> {
                            conn.close();
                            handler.handle(Future.succeededFuture(JsonResult.succ(updateResult)));
                        }, err -> fail(handler, RetCode.FAILURE.val(), err.getMessage()));
            } else {
                connectFailure(handler);
            }
        });
        return this;
    }

    /**
     * execute other operations like create table statement.
     *
     * @param sql
     * @param handler
     */
    @Override
    public DataAccessService execute(String sql, Handler<AsyncResult<JsonObject>> handler) {
        postgreClient(handler).getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection conn = res.result();
                conn.rxExecute(sql)
                        .subscribe(v -> {
                            conn.close();
                            handler.handle(Future.succeededFuture(JsonResult.succ()));
                        }, err -> fail(handler, RetCode.FAILURE.val(), err.getMessage()));
            } else {
                connectFailure(handler);
            }
        });
        return this;
    }
}
