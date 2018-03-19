package org.mendora.service.scanner;

import com.google.inject.AbstractModule;
import io.vertx.rxjava.core.Vertx;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * created by:xmf
 * date:2018/3/19
 * description:
 */
@Slf4j
@RequiredArgsConstructor
public class ServiceProxyBinder extends AbstractModule {
    private static final String MODULE_NAME = "SERVICE_PROXY_BINDER:";
    @NonNull
    private List<Object> clazzs;
    @NonNull
    private Vertx vertx;

    @Override
    protected void configure() {
        clazzs.forEach(clazz -> {
            try {
                // Object t = clazz.getMethod("createProxy", Vertx.class).invoke(null, vertx);
                //bind(clazz.getClass()).toInstance(clazz);
            } catch (Exception e) {
                log.error(MODULE_NAME + e.getMessage());
            }
        });

    }
}
