package org.mendora.web.binder;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;
import io.vertx.rxjava.ext.auth.jwt.JWTAuth;
import io.vertx.rxjava.ext.web.Router;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mendora.web.aop.logger.RouteLogging;
import org.mendora.web.aop.logger.RouteLoggingInterceptor;
import org.mendora.web.auth.WebAuth;
import org.mendora.web.efficiency.result.WebResult;

/**
 * Created by kam on 2018/3/18.
 */
@RequiredArgsConstructor
public class WebBinder extends AbstractModule {
    @NonNull
    private Router router;

    @NonNull
    private WebAuth webAuth;

    @NonNull
    private WebResult webResult;

    @Override
    protected void configure() {
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(RouteLogging.class), new RouteLoggingInterceptor());
    }

    @Provides
    @Singleton
    public Router provideRouter() {
        return this.router;
    }

    @Provides
    @Singleton
    public WebAuth provideWebAuth() {
        return this.webAuth;
    }

    @Provides
    @Singleton
    public JWTAuth provideJWTAuth() {
        return this.webAuth.jwtAuth();
    }

    @Provides
    @Singleton
    public WebResult provideWebResult() {
        return this.webResult;
    }

}
