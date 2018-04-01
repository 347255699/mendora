package org.mendora.data.binder;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import io.vertx.rxjava.ext.asyncsql.AsyncSQLClient;
import io.vertx.rxjava.ext.mongo.MongoClient;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mendora.data.auth.DBAuth;

/**
 * created by:xmf
 * date:2018/3/19
 * description:
 */
@RequiredArgsConstructor
public class DataBinder extends AbstractModule {
    @NonNull
    private AsyncSQLClient postgreSQLClient;
    @NonNull
    private MongoClient mongoClient;
    @NonNull
    private DBAuth dbAuth;

    @Override
    protected void configure() {
        super.configure();
    }

    @Provides
    public AsyncSQLClient providePostgreSQLClient() {
        return this.postgreSQLClient;
    }

    @Provides
    public MongoClient provideMongoClient() {
        return mongoClient;
    }

    @Provides
    public DBAuth provideDbAuth() {
        return dbAuth;
    }
}
