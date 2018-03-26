package org.mendora.data.service.ebService;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.UpdateResult;
import io.vertx.rxjava.core.eventbus.Message;
import io.vertx.rxjava.ext.asyncsql.AsyncSQLClient;
import io.vertx.rxjava.ext.sql.SQLConnection;
import lombok.extern.slf4j.Slf4j;
import org.mendora.data.client.ClientHolder;
import org.mendora.util.constant.SqlReferences;
import org.mendora.util.result.JsonResult;

/**
 * Created by kam on 2018/3/12.
 */
@Slf4j
public class DataAccesser {
    private static final String MODULE_NAME = "DATA_ACCESSER:";

//    private static AsyncSQLClient postgreClient() {
//        if (ClientHolder.postgre() == null)
//            throw new RuntimeException("the postgre client not ready, please wait a moment and try again.");
//        return ClientHolder.postgre();
//    }

    /**
     * execute find sql statement without params
     *
     * @param msg
     */
    public static void query(Message<String> msg) {
        String findStatement = msg.body();
        connecting(msg, conn -> {
            conn.rxQuery(findStatement)
                    .map(ResultSet::getRows)
                    .map(JsonArray::new)
                    .subscribe(rows -> {
                        conn.close();
                        msg.reply(JsonResult.succWithRows(rows));
                    }, err -> msg.reply(JsonResult.fail(err)));
        });
    }

    /**
     * execute find sql statement with params
     *
     * @param msg
     */
    public static void queryWithParams(Message<JsonObject> msg) {
        JsonObject doc = msg.body();
        String findStatement = doc.getString(SqlReferences.STATEMENT.val());
        JsonArray params = doc.getJsonArray(SqlReferences.PARAMS.val());
        connecting(msg, conn -> {
            conn.rxQueryWithParams(findStatement, params)
                    .map(ResultSet::getRows)
                    .map(JsonArray::new)
                    .subscribe(rows -> {
                        conn.close();
                        msg.reply(JsonResult.succWithRows(rows));
                    }, err -> msg.reply(JsonResult.fail(err)));
        });
    }

    /**
     * find one record without params.
     *
     * @param msg
     */
    public static void querySingle(Message<String> msg) {
        String findStatement = msg.body();
        connecting(msg, conn -> {
            conn.rxQuery(findStatement)
                    .map(ResultSet::getRows)
                    .map(JsonArray::new)
                    .subscribe(rows -> {
                        conn.close();
                        msg.reply(JsonResult.succ(rows.getJsonObject(0)));
                    }, err -> msg.reply(JsonResult.fail(err)));
        });
    }

    /**
     * find one record with params.
     *
     * @param msg
     */
    public static void querySingleWithParams(Message<JsonObject> msg) {
        JsonObject doc = msg.body();
        String findStatement = doc.getString(SqlReferences.STATEMENT.val());
        JsonArray params = doc.getJsonArray(SqlReferences.PARAMS.val());
        connecting(msg, conn -> {
            conn.rxQueryWithParams(findStatement, params)
                    .map(ResultSet::getRows)
                    .map(JsonArray::new)
                    .subscribe(rows -> {
                        conn.close();
                        msg.reply(JsonResult.succ(rows.getJsonObject(0)));
                    }, err -> msg.reply(JsonResult.fail(err)));
        });
    }

    /**
     * executing update/insert/delete sql statement without params
     *
     * @param msg
     */
    public static void update(Message<String> msg) {
        String updateSstatement = msg.body();
        connecting(msg, conn -> {
            conn.rxUpdate(updateSstatement)
                    .map(UpdateResult::toJson)
                    .subscribe(result -> {
                        conn.close();
                        msg.reply(JsonResult.succ(result));
                    }, err -> msg.reply(JsonResult.fail(err)));
        });
    }

    /**
     * execute update/insert/delete statement with params
     *
     * @param msg
     */
    public static void updateWithParams(Message<JsonObject> msg) {
        JsonObject doc = msg.body();
        String updateStatement = doc.getString(SqlReferences.STATEMENT.val());
        JsonArray params = doc.getJsonArray(SqlReferences.PARAMS.val());
        connecting(msg, conn -> {
            conn.rxUpdateWithParams(updateStatement, params)
                    .map(UpdateResult::toJson)
                    .subscribe(result -> {
                        conn.close();
                        msg.reply(JsonResult.succ(result));
                    }, err -> msg.reply(JsonResult.fail(err)));
        });
    }

    /**
     * execute other operations like create table statement.
     *
     * @param msg
     */
    public static void execute(Message<String> msg) {
        String sqlStatement = msg.body();
        connecting(msg, conn -> {
            conn.rxExecute(sqlStatement)
                    .subscribe(v -> msg.reply(JsonResult.succ()), err -> msg.reply(JsonResult.fail(err)));
        });
    }

    /**
     * connecting postgreSQL Server
     *
     * @param msg
     * @param handler
     */
    private static void connecting(Message<?> msg, SQLConnectionHandler handler) {
        postgreClient().getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection conn = res.result();
                handler.handle(conn);
            } else {
                msg.reply(JsonResult.fail(res.cause()));
            }
        });
    }
}
