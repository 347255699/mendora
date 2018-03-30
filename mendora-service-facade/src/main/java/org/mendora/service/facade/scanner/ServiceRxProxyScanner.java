package org.mendora.service.facade.scanner;

import com.google.inject.Injector;
import com.google.inject.matcher.Matchers;
import org.mendora.service.facade.aop.Monitor;
import org.mendora.service.facade.aop.MonitorMethodInterceptor;
import org.mendora.util.scanner.PackageScannerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * created by:xmf
 * date:2018/3/19
 * description:
 */
public class ServiceRxProxyScanner {
    private Logger log = LoggerFactory.getLogger(ServiceRxProxyScanner.class);
    private static final String MODULE_NAME = "SERVICE_PROXY_SCANNER:";

    /**
     * scanning all the com.udeafx.service.rear.service proxy blow target package.
     *
     * @param packagePath
     * @return
     */
    public ServiceRxProxyBinder scan(String packagePath, Injector injector) {
        List<String> names = new PackageScannerImpl<>(packagePath, this.getClass().getClassLoader())
                .classNames(this.getClass().getName());
        List<Object> proxyInstances = new ArrayList<>();
        List<Class> facades = new ArrayList<>();
        List<Class> rxProxys = new ArrayList<>();
        for (String name : names) {
            try {
                Class facade = Class.forName(name);
                if (facade.isAnnotationPresent(ServiceFacade.class)) {
                    log.info(MODULE_NAME + name);
                    ServiceFacade serviceFacade = (ServiceFacade) facade.getAnnotation(ServiceFacade.class);
                    Class proxy = serviceFacade.proxy();
                    Class rxProxy = serviceFacade.rxProxy();
                    proxyInstances.add(injector.getInstance(proxy));
                    rxProxys.add(rxProxy);
                    facades.add(facade);
                }
            } catch (ClassNotFoundException e) {
                log.error(MODULE_NAME + e.getMessage());
            }
        }
        injector = injector.createChildInjector(binder -> {
            binder.bindInterceptor(Matchers.any(), Matchers.annotatedWith(Monitor.class), new MonitorMethodInterceptor());
            if (facades != null && proxyInstances != null) {
                for (int i = 0; i < facades.size(); i++)
                    binder.bind(facades.get(i)).toInstance(proxyInstances.get(i));
            }
        });
        return new ServiceRxProxyBinder(rxProxys, injector);
    }

}
