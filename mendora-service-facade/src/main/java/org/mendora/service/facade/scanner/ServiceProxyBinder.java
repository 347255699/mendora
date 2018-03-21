package org.mendora.service.facade.scanner;

import com.google.inject.AbstractModule;
import io.vertx.rxjava.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * created by:xmf
 * date:2018/3/19
 * description:
 */
public class ServiceProxyBinder extends AbstractModule {
    private Logger log = LoggerFactory.getLogger(ServiceProxyBinder.class);
    private static final String MODULE_NAME = "SERVICE_PROXY_BINDER:";
    private List<Class<Object>> clazzs;
    private Vertx vertx;

    public ServiceProxyBinder(List<Class<Object>> clazzs, Vertx vertx) {
        this.clazzs = clazzs;
        this.vertx = vertx;
    }

    @Override
    protected void configure() {
        clazzs.forEach(clazz -> {
            try {
                Object instance = clazz.getMethod("createProxy", Vertx.class).invoke(null, vertx);
                bind(clazz).toInstance(instance);
            } catch (Exception e) {
                log.error(MODULE_NAME + e.getMessage());
            }
        });
    }
}
