package org.mendora.data.client;

import com.google.inject.Inject;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.asyncsql.AsyncSQLClient;
import io.vertx.rxjava.ext.asyncsql.PostgreSQLClient;
import io.vertx.rxjava.ext.mongo.MongoClient;
import org.mendora.data.constant.DataConst;
import org.mendora.guice.properties.ConfigHolder;
import org.mendora.util.result.JsonResult;

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

    /**
     * parser postgre uri -> json.
     *
     * @param connectionStr
     * @return
     */
    private JsonObject parserPostgreUri(String connectionStr) {
        JsonObject postgreUri = new JsonObject();
        connectionStr = connectionStr.substring(connectionStr.indexOf(":") + 3);
        String[] uri = connectionStr.split("\\?");
        String[] params = uri[1].split("&");
        String[] temp;
        for (String param : params) {
            temp = param.split("=");
            postgreUri.put(temp[0], temp[1]);
        }
        int i = uri[0].lastIndexOf("/") + 1;
        postgreUri.put("database", uri[0].substring(i));
        String host = uri[0].substring(0, i - 1);
        i = host.lastIndexOf(":");
        postgreUri.put("port", Integer.parseInt(host.substring(i + 1)));
        host = host.substring(0, i);
        temp = host.split("@");
        postgreUri.put("host", temp[1]);
        String[] user = temp[0].split(":");
        postgreUri.put("username", user[0]);
        // string parse integer.
        postgreUri.put("password", user[1]);
        postgreUri.put("maxPoolSize", Integer.parseInt(postgreUri.getString("maxPoolSize")));
        postgreUri.put("queryTimeout", Integer.parseInt(postgreUri.getString("queryTimeout")));
        return postgreUri;
    }

    /**
     * create postgresql client.
     *
     * @return
     */
    public AsyncSQLClient createPostgreSQLClient() {
        JsonObject postgreUri = parserPostgreUri(configHolder.property(DataConst.DATA_DB_POSTGRE_URI));
        return PostgreSQLClient.createShared(vertx, postgreUri);
    }

    /**
     * create mongodb client.
     *
     * @return
     */
    public MongoClient createMongoClient() {
        JsonObject mongoUri = JsonResult.one()
                .put("connection_string", configHolder.property(DataConst.DATA_DB_MONGO_URI));
        return MongoClient.createShared(vertx, mongoUri);
    }
}
