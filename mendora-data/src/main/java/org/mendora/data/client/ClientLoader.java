package org.mendora.data.client;

import com.google.inject.Inject;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.asyncsql.AsyncSQLClient;
import io.vertx.rxjava.ext.asyncsql.PostgreSQLClient;
import org.mendora.data.constant.DataConst;
import org.mendora.guice.properties.ConfigHolder;

/**
 * created by:xmf
 * date:2018/3/12
 * description:
 */
public class ClientLoader {
    private Vertx vertx;
    private ConfigHolder configHolder;

    @Inject
    public ClientLoader(Vertx vertx, ConfigHolder configHolder) {
        this.vertx = vertx;
        this.configHolder = configHolder;
    }

    public AsyncSQLClient createPostgreSQLClient() {
        // loading postgreSql db config.
        JsonObject postgreSQLClientConfig = new JsonObject()
                .put("host", configHolder.property(DataConst.DATA_DB_POSTGRE_HOST))
                .put("port", Integer.parseInt(configHolder.property(DataConst.DATA_DB_POSTGRE_PORT)))
                .put("maxPoolSize", Integer.parseInt(configHolder.property(DataConst.DATA_DB_POSTGRE_MAX_POOL_SIZE)))
                .put("username", configHolder.property(DataConst.DATA_DB_POSTGRE_USERNAME))
                .put("password", configHolder.property(DataConst.DATA_DB_POSTGRE_PASSWORD))
                .put("database", configHolder.property(DataConst.DATA_DB_POSTGRE_DATABASE))
                .put("charset", configHolder.property(DataConst.DATA_DB_POSTGRE_CHARSET))
                .put("queryTimeout", Integer.parseInt(configHolder.property(DataConst.DATA_DB_POSTGRE_QUERY_TIMEOUT)));
        return PostgreSQLClient.createShared(vertx, postgreSQLClientConfig);
    }
}
