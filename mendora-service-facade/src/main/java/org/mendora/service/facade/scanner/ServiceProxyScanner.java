package org.mendora.service.facade.scanner;

import com.google.inject.Injector;
import io.vertx.rxjava.core.Vertx;
import org.mendora.util.scanner.PackageScannerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

/**
 * created by:xmf
 * date:2018/3/19
 * description:
 */
public class ServiceProxyScanner {
    private Logger log = LoggerFactory.getLogger(ServiceProxyScanner.class);
    private static final String MODULE_NAME = "SERVICE_PROXY_SCANNER:";
    private Vertx vertx;

    @Inject
    public ServiceProxyScanner(Vertx vertx) {
        this.vertx = vertx;
    }

    /**
     * scanning all the service proxy blow target package.
     *
     * @param packagePath
     * @return
     */
    public void scan(String packagePath, Injector injector) {
        List<String> names = new PackageScannerImpl<>(packagePath, this.getClass().getClassLoader())
                .classNames(this.getClass().getName());
        names.forEach(name -> {
            log.info(MODULE_NAME + name);
            try {
                Class<Object> clazz = (Class<Object>) Class.forName(name);
                if (clazz.isAnnotationPresent(ServiceFacade.class)) {
                    ServiceFacade facade = clazz.getAnnotation(ServiceFacade.class);
                    Class proxy = facade.proxy();
                    Class rxProxy = facade.rxProxy();
                    injector.createChildInjector(binder -> binder.bind(clazz).toInstance(injector.getInstance(proxy)));
                    injector.createChildInjector(binder -> binder.bind(rxProxy).toInstance(injector.getInstance(rxProxy)));
                }
            } catch (ClassNotFoundException e) {
                log.error(MODULE_NAME + e.getMessage());
            }
        });
//        List<Class<Object>> clazzs = new ArrayList<>();
//        for (String name : names) {
//            try {
//                clazzs.add((Class<Object>) Class.forName(name));
//            } catch (ClassNotFoundException e) {
//                log.error(MODULE_NAME + e.getMessage());
//            }
//        }
//        return new ServiceProxyBinder(clazzs, vertx);
    }

}
