package org.mendora.base.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;

/**
 * Created by kam on 2018/2/4.
 */
public abstract class SimpleVerticle extends AbstractVerticle {
    public DeploymentOptions options() {
        return new DeploymentOptions();
    }
}
