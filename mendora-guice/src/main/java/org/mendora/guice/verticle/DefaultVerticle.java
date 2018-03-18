package org.mendora.guice.verticle;

import com.google.inject.Injector;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;

/**
 * Created by kam on 2018/2/4.
 */
public abstract class DefaultVerticle extends AbstractVerticle {
    protected Injector injector;

    public DeploymentOptions options() {
        return new DeploymentOptions();
    }

    public void setInjector(Injector injector) {
        this.injector = injector;
    }
}
