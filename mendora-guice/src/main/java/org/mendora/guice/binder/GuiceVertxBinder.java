package org.mendora.guice.binder;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.vertx.rxjava.core.Vertx;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mendora.guice.properties.ConfigHolder;

/**
 * Created by kam on 2018/3/18.
 */
@RequiredArgsConstructor
public class GuiceVertxBinder extends AbstractModule {

    @NonNull
    private ConfigHolder config;

    @NonNull
    private Vertx vertx;

    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    public ConfigHolder provideConfig() {
        return this.config;
    }

    @Provides
    @Singleton
    public Vertx provideVertx() {
        return this.vertx;
    }

}
