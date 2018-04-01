package org.mendora.guice.verticles;

import com.google.inject.Inject;
import com.google.inject.Injector;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.rxjava.core.Vertx;
import org.mendora.guice.properties.ConfigHolder;

/**
 * Created by kam on 2018/2/4.
 */
public abstract class DefaultVerticle extends AbstractVerticle {
    @Inject
    protected Vertx vertx;
    @Inject
    protected ConfigHolder configHolder;
    protected Injector injector;

    public DeploymentOptions options() {
        return new DeploymentOptions();
    }

    public void setInjector(Injector injector) {
        this.injector = injector;
    }
}
