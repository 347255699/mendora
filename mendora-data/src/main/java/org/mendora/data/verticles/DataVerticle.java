package org.mendora.data.verticles;

import io.vertx.core.DeploymentOptions;
import lombok.extern.slf4j.Slf4j;
import org.mendora.base.verticles.SimpleVerticle;

/**
 * created by:xmf
 * date:2018/3/12
 * description:
 */
@Slf4j
public class DataVerticle extends SimpleVerticle {
    @Override
    public DeploymentOptions options() {
        return super.options();
    }

    @Override
    public void start() throws Exception {
        // common query interface
    }
}
