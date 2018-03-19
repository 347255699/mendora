package org.mendora.data.binder;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import io.vertx.rxjava.ext.asyncsql.AsyncSQLClient;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * created by:xmf
 * date:2018/3/19
 * description:
 */
@RequiredArgsConstructor
public class DataBinder extends AbstractModule{
    @NonNull
    private AsyncSQLClient postgreSQLClient;

    @Override
    protected void configure() {
        super.configure();
    }

    @Provides
    public AsyncSQLClient providePostgreSQLClient(){
        return this.postgreSQLClient;
    }
}
