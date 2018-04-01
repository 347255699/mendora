package org.mendora.service.facade.scanner;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;

import java.util.List;

/**
 * Created by kam on 2018/4/1.
 */
public class ServiceProxyBinder extends AbstractModule {
    private List<Class> proxys;
    private List<Class> facades;
    private Injector injector;

    public ServiceProxyBinder(List<Class> facades, List<Class> proxys, Injector injector) {
        this.facades = facades;
        this.proxys = proxys;
        this.injector = injector;
    }

    @Override
    protected void configure() {
        if (facades != null && injector != null && proxys != null) {
            for (int i = 0; i < facades.size(); i++)
                bind(facades.get(i)).toInstance(injector.getInstance(proxys.get(i)));
        }
    }
}
