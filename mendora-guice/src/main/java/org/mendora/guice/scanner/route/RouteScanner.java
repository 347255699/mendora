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
    private static final String DEFAULT_METHOD_NAME = "route";
    private ConfigHolder configHolder;
    private Vertx vertx;
    private Router router;

    @Inject
    public RouteScanner(ConfigHolder configHolder, Vertx vertx, Router router) {
        this.configHolder = configHolder;
        this.vertx = vertx;
        this.router = router;
    }

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
        clazz.getMethod(DEFAULT_METHOD_NAME, String.class).invoke(instance, prefix);
        Method[] methods = clazz.getMethods();
        Arrays.asList(methods).forEach(method -> {
            if (method.isAnnotationPresent(RequestRouting.class)) {
                RequestRouting requestRouting = method.getAnnotation(RequestRouting.class);
                String path = requestRouting.path();
                path = (StringUtils.isNotEmpty(prefix)) ? prefix + path : path;
                router.route(requestRouting.method(), path).handler(rc -> {
                    try {
                        method.invoke(instance, rc);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                });
            }
        });
    }

    /**
     * launching http server.
     */
    private void launchHttpServer() {
        String webServerPort = configHolder.property(BaseConst.BASE_WEB_LISTENNING_PORT);
        log.info(MODULE_NAME + "all the \"routes\" deployed");
        router.getRoutes().forEach(r -> log.info(r.getPath()));
        vertx.createHttpServer().requestHandler(router::accept)
                .listen(Integer.parseInt(webServerPort));
        log.info(MODULE_NAME + "web server listenning at port:" + webServerPort);
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
                        log.error(e.getMessage());
                    }
                    return clazz;
                })
                .filter(clazz -> clazz.isAnnotationPresent(Route.class))
                .subscribe(clazz -> invokeRequestRouting(clazz, injector),
                        err -> log.error(MODULE_NAME + err.getMessage()),
                        this::launchHttpServer);
    }
}
