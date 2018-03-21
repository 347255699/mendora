package org.mendora.guice.scanner.route;

import com.google.inject.Inject;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.web.Router;
import org.mendora.guice.properties.ConfigHolder;

/**
 * created by:xmf
 * date:2018/3/21
 * description:
 */
public class AbstractRoute {
    @Inject
    protected Vertx vertx;
    @Inject
    protected ConfigHolder configHolder;
    @Inject
    protected Router router;
    public void route(String prefix){}
}
