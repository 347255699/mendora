package org.mendora.base.utils;


import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.eventbus.EventBus;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
public class VertxHolder {
    private static Vertx vertx;

    public static void setVertx(Vertx _vertx) {
        vertx = _vertx;
    }

    public static Vertx vertx() {
        return vertx;
    }

    public static EventBus eventBus() {
        return vertx.eventBus();
    }
}
