package org.mendora.guice.scanner.serviceProvider;

import com.google.inject.Inject;
import com.google.inject.Injector;
import lombok.extern.slf4j.Slf4j;
import org.mendora.guice.scanner.PackageScannerImpl;
import rx.Observable;

import java.util.List;

/**
 * created by:xmf
 * date:2018/3/19
 * description:
 */
@Slf4j
public class ServiceProviderScanner {
    public static final String MODULE_NAME = "SERVICE_PROVIDER_SCANNER:";
    public static final String REGISTER_METHOD_NAME = "register";

    @Inject
    public ServiceProviderScanner() {
    }

    /**
     * register service
     *
     * @param clazz
     */
    private void registerService(Class<?> clazz, Injector injector) {
        try {
            log.info(MODULE_NAME + clazz.getName());
            Object o = injector.getInstance(clazz);
            clazz.getMethod(REGISTER_METHOD_NAME).invoke(o);
        } catch (Exception e) {
            log.error(MODULE_NAME + e.getCause().getMessage());
        }
    }

    /**
     * scanning service provider
     *
     * @param packagePath
     * @param cl
     */
    public void scan(String packagePath, ClassLoader cl, Injector injector) {
        List<Class<?>> clazzs = new PackageScannerImpl<>().classWithNoFilter(packagePath, cl);
        log.info(MODULE_NAME + clazzs.size());
        Observable.from(clazzs)
                .filter(clazz -> clazz.isAnnotationPresent(ServiceProvider.class))
                .subscribe(clazz -> registerService(clazz, injector),
                        err -> log.error(MODULE_NAME + err.getMessage()),
                        () -> log.info(MODULE_NAME + "all the service provider scanning over."));
    }
}
