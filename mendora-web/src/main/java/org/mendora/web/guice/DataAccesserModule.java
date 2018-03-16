package org.mendora.web.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.mendora.base.utils.VertxHolder;
import org.mendora.service.dataAccesser.rxjava.DataAccessService;

/**
 * created by:xmf
 * date:2018/3/16
 * description:
 */
public class DataAccesserModule extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Provides
    DataAccessService provideDataAccessService() {
        return DataAccessService.createProxy(VertxHolder.vertx());
    }
}
