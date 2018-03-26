package org.mendora.data.verticles;

import io.vertx.core.DeploymentOptions;
import io.vertx.rxjava.ext.asyncsql.AsyncSQLClient;
import io.vertx.rxjava.ext.mongo.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.mendora.data.binder.DataBinder;
import org.mendora.data.client.ClientLoader;
import org.mendora.data.constant.DataConst;
import org.mendora.guice.scanner.serviceProvider.ServiceProviderScanner;
import org.mendora.guice.verticles.DefaultVerticle;

/**
 * created by:xmf
 * date:2018/3/12
 * description:
 */
@Slf4j
public class DataVerticle extends DefaultVerticle {
    @Override
    public DeploymentOptions options() {
        return super.options();
    }

    @Override
    public void start() throws Exception {
        // injecting db client instance
        ClientLoader clientHolder = injector.getInstance(ClientLoader.class);
        AsyncSQLClient postgreSQLClient = clientHolder.createPostgreSQLClient();
        MongoClient mongoClient = clientHolder.createMongoClient();
        injector = injector.createChildInjector(new DataBinder(postgreSQLClient, mongoClient));

        // scanning service provider implementation
        ServiceProviderScanner scanner = injector.getInstance(ServiceProviderScanner.class);
        scanner.scan(configHolder.property(DataConst.DATA_SERVICE_INTO_PACKAGE), DataVerticle.class.getClassLoader(), injector);
    }
}
