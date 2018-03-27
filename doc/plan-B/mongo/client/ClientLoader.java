package com.udeafx.data.client;

import com.crmfx.guice.properties.ConfigHolder;
import com.crmfx.util.result.JsonResult;
import com.google.inject.Inject;
import com.udeafx.data.constant.DataConst;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.mongo.MongoClient;

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
     * create mongodb client.
     * @return
     */
    public MongoClient createMongoClient(){
        JsonObject mongoUri = JsonResult.one()
                .put("connection_string", configHolder.property(DataConst.DATA_DB_CONNECTING_URI));
         return MongoClient.createShared(vertx, mongoUri);
    }

}
