package org.mendora.service.facade.scanner;

import io.vertx.rxjava.core.Vertx;
import org.mendora.util.scanner.PackageScannerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * created by:xmf
 * date:2018/3/19
 * description:
 */
public class ServiceProxyScanner {
    private Logger log = LoggerFactory.getLogger(ServiceProxyScanner.class);
    private static final String MODULE_NAME = "SERVICE_PROXY_SCANNER:";
    private static final String PREFIX = "rxjava";
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
    public ServiceProxyBinder scan(String packagePath) {
        List<String> names = new PackageScannerImpl<>(packagePath, this.getClass().getClassLoader())
                .classNames(this.getClass().getName());
        names.forEach(name -> log.info(MODULE_NAME + name));
        List<Class<Object>> clazzs = new ArrayList<>();
        for (String name : names) {
            try {
                if (rxServiceProxy(name))
                    clazzs.add((Class<Object>) Class.forName(name));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new ServiceProxyBinder(clazzs, vertx);
    }

    /**
     * scanning only rx service proxy.
     *
     * @param name
     * @return
     */
    public boolean rxServiceProxy(String name) {
        return name.contains(PREFIX);
    }
}
