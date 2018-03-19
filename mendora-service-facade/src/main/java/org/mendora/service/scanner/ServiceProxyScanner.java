package org.mendora.service.scanner;

import io.vertx.rxjava.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.mendora.util.scanner.PackageScannerImpl;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * created by:xmf
 * date:2018/3/19
 * description:
 */
@Slf4j
public class ServiceProxyScanner {
    private static final String MODULE_NAME = "SERVICE_PROXY_SCANNER";
    private static final String PROXY_SERVICE_PACKAGE_PATH = "org.mendora.service";
    private static final String PREFIX = "rxjava";
    private Vertx vertx;

    @Inject
    public ServiceProxyScanner(Vertx vertx) {
        this.vertx = vertx;
    }

    public ServiceProxyBinder scan() {
        List<String> names = new PackageScannerImpl<>(PROXY_SERVICE_PACKAGE_PATH, this.getClass().getClassLoader())
                .classNames(this.getClass().getName());
        List<Class<Object>> clazzs = new ArrayList<>();
        for (String name : names) {
            try {
                if (isProxy(name))
                    clazzs.add((Class<Object>) Class.forName(name));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new ServiceProxyBinder(clazzs, vertx);
    }

    public boolean isProxy(String name) {
        return name.contains(PREFIX);
    }
}
