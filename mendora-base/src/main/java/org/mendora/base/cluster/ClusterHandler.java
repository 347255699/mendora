package org.mendora.base.cluster;

import io.vertx.rxjava.core.Vertx;

/**
 * created by:xmf
 * date:2017/10/31
 * description:
 */
@FunctionalInterface
public interface ClusterHandler {
    void handle(Vertx vertx);
}
