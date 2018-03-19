package org.mendora.service.scanner;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.mendora.util.scanner.PackageScannerImpl;
import rx.Observable;

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

    public void scan() {
        List<String> names = new PackageScannerImpl<>(PROXY_SERVICE_PACKAGE_PATH, this.getClass().getClassLoader())
                .classNames(this.getClass().getName());
        val clazzs = new ArrayList<>();
        for(String name : names){
            try {

                clazzs.add(Class.forName(name));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        Observable.from(names)
                .filter(this::isProxy)
                .subscribe(name -> {

                        }, err -> log.error(err.getMessage()),
                        () -> log.info(MODULE_NAME + "all the service proxy scanning over."));
    }

    public boolean isProxy(String name) {
        return name.contains(PREFIX);
    }
}
