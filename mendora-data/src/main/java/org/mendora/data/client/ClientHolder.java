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

    public static AsyncSQLClient postgre() {
        return postgreSQLClient;
    }

    public static void init(Vertx vertx) {
        // loading postgreSql db config.
        JsonObject postgreSQLClientConfig = new JsonObject()
                .put("host", ConfigHolder.property(DataConst.DATA_DB_POSTGRE_HOST))
                .put("port", ConfigHolder.property(DataConst.DATA_DB_POSTGRE_PORT))
                .put("maxPoolSize", ConfigHolder.property(DataConst.DATA_DB_POSTGRE_MAX_POOL_SIZE))
                .put("username", ConfigHolder.property(DataConst.DATA_DB_POSTGRE_USERNAME))
                .put("password", ConfigHolder.property(DataConst.DATA_DB_POSTGRE_PASSWORD))
                .put("database", ConfigHolder.property(DataConst.DATA_DB_POSTGRE_DATABASE))
                .put("charset", ConfigHolder.property(DataConst.DATA_DB_POSTGRE_CHARSET))
                .put("queryTimeout", ConfigHolder.property(DataConst.DATA_DB_POSTGRE_QUERY_TIMEOUT));
        postgreSQLClient = PostgreSQLClient.createShared(vertx, postgreSQLClientConfig);
    }

    public static void close() {
        if (postgreSQLClient != null)
            postgreSQLClient.close();
    }
}
