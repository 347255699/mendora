package org.mendora.data.verticles;

import com.google.inject.Inject;
import io.vertx.core.DeploymentOptions;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.asyncsql.AsyncSQLClient;
import lombok.extern.slf4j.Slf4j;
import org.mendora.data.binder.DataBinder;
import org.mendora.data.client.ClientLoader;
import org.mendora.data.constant.DataConst;
import org.mendora.guice.properties.ConfigHolder;
import org.mendora.guice.scanner.serviceProvider.ServiceProviderScanner;
import org.mendora.guice.verticle.DefaultVerticle;

/**
 * created by:xmf
 * date:2018/3/12
 * description:
 */
@Slf4j
public class DataVerticle extends DefaultVerticle {
    @Inject
    private Vertx vertx;
    @Inject
    private ConfigHolder configHolder;

    @Override
    public DeploymentOptions options() {
        return super.options();
    }

    @Override
    public void start() throws Exception {
        // injecting postgreClient
        ClientLoader clientHolder = injector.getInstance(ClientLoader.class);
        AsyncSQLClient postgreSQLClient = clientHolder.createPostgreSQLClient();
        injector = injector.createChildInjector(new DataBinder(postgreSQLClient));
        ServiceProviderScanner scanner = injector.getInstance(ServiceProviderScanner.class);
        scanner.scan(configHolder.property(DataConst.DATA_SERVICE_INTO_PACKAGE), DataVerticle.class.getClassLoader(), injector);
    }
}
