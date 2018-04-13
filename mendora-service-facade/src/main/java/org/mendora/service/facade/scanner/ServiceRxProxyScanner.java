package org.mendora.service.facade.scanner;

import com.google.inject.Injector;
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
     * scanning all the service proxy blow target package more time.
     *
     * @param packagePaths
     * @param injector
     * @return
     */
    public Injector scan(List<String> packagePaths, Injector injector) {
        for (String path : packagePaths)
            injector = injector.createChildInjector(scan(path, injector));
        return injector;
    }

    /**
     * scanning all the service proxy blow target package.
     *
     * @param packagePath
     * @return
     */
    private ServiceRxProxyBinder scan(String packagePath, Injector injector) {
        List<String> names = new PackageScannerImpl<>(packagePath, this.getClass().getClassLoader())
                .classNames(this.getClass().getName());
        List<Class> proxys = new ArrayList<>();
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
                    facades.add(facade);
                    proxys.add(proxy);
                    rxProxys.add(rxProxy);
                }
            } catch (ClassNotFoundException e) {
                log.error(MODULE_NAME + e.getMessage());
            }
        }
        injector = injector.createChildInjector(new ServiceProxyBinder(facades, proxys, injector));
        return new ServiceRxProxyBinder(rxProxys, injector);
    }

}
