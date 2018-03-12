package org.mendora.data.client;

import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.asyncsql.AsyncSQLClient;
import io.vertx.rxjava.ext.asyncsql.PostgreSQLClient;
import org.mendora.base.properties.ConfigHolder;
import org.mendora.data.constant.DataConst;

/**
 * created by:xmf
 * date:2018/3/12
 * description:
 */
public class ClientHolder {
    private static AsyncSQLClient postgreSQLClient;

    public static void init(Vertx vertx) {
        String host = ConfigHolder.property(DataConst.DATA_DB_POSTGRE_HOST);
        JsonObject postgreSQLClientConfig = new JsonObject().put("host", host);
        AsyncSQLClient postgreSQLClient = PostgreSQLClient.createShared(vertx, postgreSQLClientConfig);
    }

    public static void close() {
        if (postgreSQLClient != null)
            postgreSQLClient.close();
    }
}
