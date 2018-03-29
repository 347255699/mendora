package org.mendora.data.service;

import com.google.inject.Inject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.UpdateResult;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.asyncsql.AsyncSQLClient;
import io.vertx.rxjava.ext.sql.SQLConnection;
import io.vertx.serviceproxy.ProxyHelper;
import lombok.extern.slf4j.Slf4j;
import org.mendora.guice.scanner.serviceProvider.ServiceProvider;
import org.mendora.service.facade.dataAccesser.PostgreAccesser;
import org.mendora.util.constant.SqlReferences;
import org.mendora.util.result.AsyncHandlerResult;

/**
 * created by:xmf
 * date:2018/3/14
 * description:
 */
@Slf4j
@ServiceProvider
public class PostgreAccesserImpl implements PostgreAccesser {
    private static final String MODULE_NAME = "POSTGRE_ACCESSER_IMPL";
    @Inject
    private AsyncSQLClient postgreSQLClient;
    @Inject
    private Vertx vertx;

    /**
     * connecting postgreSQL db failure.
     *
     * @param handler
     */
    private void connectFailure(Handler<AsyncResult<JsonObject>> handler) {
        AsyncHandlerResult.fail(MODULE_NAME + "postgreSQL db connect failure.", handler);
    }

    /**
     * register service
     */
    public void register() {
        ProxyHelper.registerService(PostgreAccesser.class, vertx.getDelegate(), this, EB_ADDRESS);
    }

    @Override
    public PostgreAccesser unRegister(Handler<AsyncResult<Void>> handler) {
        return null;
    }

    @Override
    public PostgreAccesser pause(Handler<AsyncResult<Void>> handler) {
        return null;
    }

    @Override
    public PostgreAccesser resume(Handler<AsyncResult<Void>> handler) {
        return null;
    }

    @Override
    public PostgreAccesser isRegistered(Handler<AsyncResult<Boolean>> handler) {
        return null;
    }

    /**
     * execute find sql statement without params
     *
     * @param sql
     * @param handler
     */
    @Override
    public PostgreAccesser query(String sql, Handler<AsyncResult<JsonObject>> handler) {
        postgreSQLClient.getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection conn = res.result();
                conn.rxQuery(sql)
                        .map(ResultSet::getRows)
                        .map(JsonArray::new)
                        .subscribe(rows -> {
                            conn.close();
                            AsyncHandlerResult.succWithRows(rows, handler);
                        }, err -> AsyncHandlerResult.fail(err, handler));
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
    public PostgreAccesser queryWithParams(JsonObject doc, Handler<AsyncResult<JsonObject>> handler) {
        String sql = doc.getString(SqlReferences.STATEMENT.val());
        JsonArray params = doc.getJsonArray(SqlReferences.PARAMS.val());
        postgreSQLClient.getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection conn = res.result();
                conn.rxQueryWithParams(sql, params)
                        .map(ResultSet::getRows)
                        .map(JsonArray::new)
                        .subscribe(rows -> {
                            conn.close();
                            AsyncHandlerResult.succWithRows(rows, handler);
                        }, err -> AsyncHandlerResult.fail(err, handler));
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
    public PostgreAccesser querySingle(String sql, Handler<AsyncResult<JsonObject>> handler) {
        postgreSQLClient.getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection conn = res.result();
                conn.rxQuery(sql)
                        .map(ResultSet::getRows)
                        .map(JsonArray::new)
                        .map(rows -> rows.getJsonObject(0))
                        .subscribe(record -> {
                            conn.close();
                            AsyncHandlerResult.succ(record, handler);
                        }, err -> AsyncHandlerResult.fail(err, handler));
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
    public PostgreAccesser querySingleWithParams(JsonObject doc, Handler<AsyncResult<JsonObject>> handler) {
        String sql = doc.getString(SqlReferences.STATEMENT.val());
        JsonArray params = doc.getJsonArray(SqlReferences.PARAMS.val());
        postgreSQLClient.getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection conn = res.result();
                conn.rxQueryWithParams(sql, params)
                        .map(ResultSet::getRows)
                        .map(JsonArray::new)
                        .map(rows -> rows.getJsonObject(0))
                        .subscribe(record -> {
                            conn.close();
                            AsyncHandlerResult.succ(record, handler);
                        }, err -> AsyncHandlerResult.fail(err, handler));
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
    public PostgreAccesser update(String sql, Handler<AsyncResult<JsonObject>> handler) {
        postgreSQLClient.getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection conn = res.result();
                conn.rxUpdate(sql)
                        .map(UpdateResult::toJson)
                        .subscribe(updateResult -> {
                            conn.close();
                            AsyncHandlerResult.succ(updateResult, handler);
                        }, err -> AsyncHandlerResult.fail(err, handler));
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
    public PostgreAccesser updateWithParams(JsonObject doc, Handler<AsyncResult<JsonObject>> handler) {
        String sql = doc.getString(SqlReferences.STATEMENT.val());
        JsonArray params = doc.getJsonArray(SqlReferences.PARAMS.val());
        postgreSQLClient.getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection conn = res.result();
                conn.rxUpdateWithParams(sql, params)
                        .map(UpdateResult::toJson)
                        .subscribe(updateResult -> {
                            conn.close();
                            AsyncHandlerResult.succ(updateResult, handler);
                        }, err -> AsyncHandlerResult.fail(err, handler));
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
    public PostgreAccesser execute(String sql, Handler<AsyncResult<JsonObject>> handler) {
        postgreSQLClient.getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection conn = res.result();
                conn.rxExecute(sql)
                        .subscribe(v -> {
                            conn.close();
                            AsyncHandlerResult.succ(handler);
                        }, err -> AsyncHandlerResult.fail(err, handler));
            } else {
                connectFailure(handler);
            }
        });
        return this;
    }

}
