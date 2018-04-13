package org.mendora.guice.scanner.route;

import com.google.inject.Inject;
import com.google.inject.Injector;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.web.Router;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mendora.guice.properties.BaseConst;
import org.mendora.guice.properties.ConfigHolder;
import org.mendora.util.scanner.PackageScannerImpl;
import rx.Observable;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kam on 2018/3/18.
 */
@Slf4j
public class RouteScanner {
    private static final String MODULE_NAME = "ROUTE-SCANNER:";
    private static final String ROUTE_METHOD_NAME = "route";
    @Inject
    private ConfigHolder configHolder;
    @Inject
    private Vertx vertx;
    @Inject
    private Router router;

    /**
     * invoke request routing method.
     *
     * @param clazz
     * @param injector
     */
    @SneakyThrows
    private void invokeRequestRouting(Class<?> clazz, Injector injector) {
        log.info(MODULE_NAME + clazz.getName());
        Route route = clazz.getAnnotation(Route.class);
        String prefix = route.value();
        Object instance = injector.getInstance(clazz);
        clazz.getMethod(ROUTE_METHOD_NAME, String.class).invoke(instance, prefix);
        Method[] methods = clazz.getMethods();
        for (Method method : Arrays.asList(methods)) {
            if (method.isAnnotationPresent(RequestRouting.class)) {
                RequestRouting requestRouting = method.getAnnotation(RequestRouting.class);
                String path = requestRouting.path();
                int order = requestRouting.order();
                path = (StringUtils.isNotEmpty(prefix)) ? prefix + path : path;
                router.route(requestRouting.method(), path).order(order).handler(rc -> {
                    try {
                        method.invoke(instance, rc);
                    } catch (Exception e) {
                        log.error(MODULE_NAME + e.getMessage());
                    }
                });
            }
        }
    }

    /**
     * launching http server.
     */
    private void launchHttpServer() {
        String webServerPort = configHolder.property(BaseConst.BASE_WEB_LISTENNING_PORT);
        log.info("{}all the \"routes\" deployed", MODULE_NAME);
        router.getRoutes().forEach(r -> log.info(r.getPath()));
        // when the 'port' field was empty than default port value 80.
        vertx.createHttpServer().requestHandler(router::accept)
                .listen(Integer.parseInt(StringUtils.isNotEmpty(webServerPort) ? webServerPort : "80"));
        log.info("{}web server listenning at port:{}", MODULE_NAME, webServerPort);
    }

    /**
     * scanning route
     *
     * @param packagePath
     * @param injector
     */
    public void scan(String packagePath, Injector injector, ClassLoader cl) {
        List<String> names = new PackageScannerImpl(packagePath, cl).classNames();
        log.info(MODULE_NAME + names.size());
        Observable.from(names)
                .map(name -> {
                    Class clazz = null;
                    try {
                        clazz = Class.forName(name);
                    } catch (ClassNotFoundException e) {
                        Observable.error(e);
                    }
                    return clazz;
                })
                .filter(clazz -> clazz.isAnnotationPresent(Route.class))
                .subscribe(clazz -> invokeRequestRouting(clazz, injector),
                        err -> log.error(MODULE_NAME + err.getMessage()),
                        this::launchHttpServer);
    }
}
