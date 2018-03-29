package org.mendora.service.facade.scanner;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;

import java.util.List;

/**
 * created by:xmf
 * date:2018/3/29
 * description:
 */
public class ServiceRxProxyBinder extends AbstractModule {

    private List<Class> rxProxys;
    private Injector injector;

    public ServiceRxProxyBinder(List<Class> rxProxys, Injector injector) {
        this.rxProxys = rxProxys;
        this.injector = injector;
    }

    @Override
    protected void configure() {
        if (rxProxys != null && injector != null) {
            for (Class rxProxy : rxProxys)
                bind(rxProxy).toInstance(injector.getInstance(rxProxy));
        }

    }
}
