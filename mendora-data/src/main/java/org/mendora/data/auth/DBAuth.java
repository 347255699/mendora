package org.mendora.data.auth;

import com.google.inject.Inject;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.mongo.HashSaltStyle;
import io.vertx.ext.auth.mongo.impl.DefaultHashStrategy;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.auth.User;
import io.vertx.rxjava.ext.auth.mongo.HashStrategy;
import io.vertx.rxjava.ext.auth.mongo.MongoAuth;
import io.vertx.rxjava.ext.mongo.MongoClient;
import org.mendora.data.constant.DataConst;
import org.mendora.guice.properties.ConfigHolder;
import org.mendora.util.result.JsonResult;
import rx.Single;

/**
 * created by:xmf
 * date:2018/3/26
 * description:
 */
public class DBAuth {
    private Vertx vertx;
    private ConfigHolder configHolder;
    private MongoAuth mongoAuth;
    private MongoClient mongoClient;

    @Inject
    public DBAuth(Vertx vertx, ConfigHolder configHolder, MongoClient mongoClient) {
        this.vertx = vertx;
        this.configHolder = configHolder;
        this.mongoClient = mongoClient;
        initMongoAuth();
    }

    /**
     * initialization mongo auth.
     */
    private void initMongoAuth() {
        JsonObject authProperties = JsonResult.empty();
        mongoAuth = MongoAuth.create(mongoClient, authProperties);
        HashStrategy strategy = HashStrategy.newInstance(new DefaultHashStrategy());
        strategy.setSaltStyle(HashSaltStyle.EXTERNAL);
        strategy.setExternalSalt(configHolder.property(DataConst.DATA_DB_AUTH_DEFAULT_SALT));
        mongoAuth.setHashStrategy(strategy);
    }

    /**
     * get mongo auth instance
     *
     * @return
     */
    public MongoAuth mongoAuth() {
        return mongoAuth;
    }

    /**
     * authenticate user account and password.
     *
     * @return
     */
    public Single<User> rxAuthenticateUsr(JsonObject authInfo) {
        return mongoAuth.rxAuthenticate(authInfo);
    }

}
