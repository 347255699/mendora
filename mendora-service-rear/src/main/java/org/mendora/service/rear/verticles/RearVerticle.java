package org.mendora.service.rear.verticles;

import io.vertx.core.DeploymentOptions;
import lombok.extern.slf4j.Slf4j;
import org.mendora.guice.properties.BaseConst;
import org.mendora.guice.scanner.serviceProvider.ServiceProviderScanner;
import org.mendora.guice.verticles.DefaultVerticle;
import org.mendora.service.facade.constant.FacadeConst;
import org.mendora.service.facade.scanner.ServiceProxyBinder;
import org.mendora.service.facade.scanner.ServiceProxyScanner;

/**
 * created by:xmf
 * date:2018/3/12
 * description:
 */
@Slf4j
public class RearVerticle extends DefaultVerticle {
    @Override
    public DeploymentOptions options() {
        return super.options();
    }

    @Override
    public void start() throws Exception {
        // injecting service proxy implementation.
        String proxyIntoPackage = configHolder.property(FacadeConst.FACADE_SERVICE_PROXY_INTO_PACKAGE);
        ServiceProxyBinder serviceProxyBinder = injector.getInstance(ServiceProxyScanner.class).scan(proxyIntoPackage);
        injector = injector.createChildInjector(serviceProxyBinder);

        // scanning service and register.
        String serviceIntoPackage = configHolder.property(BaseConst.BASE_SERVICE_PROVIDER_INTO_PACKAGE);
        ServiceProviderScanner scanner = injector.getInstance(ServiceProviderScanner.class);
        scanner.scan(serviceIntoPackage, RearVerticle.class.getClassLoader(), injector);
    }
}
