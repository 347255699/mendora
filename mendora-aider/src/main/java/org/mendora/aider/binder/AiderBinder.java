package org.mendora.aider.binder;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.vertx.rxjava.ext.auth.jwt.JWTAuth;
import io.vertx.rxjava.ext.web.Router;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mendora.aider.auth.WebAuth;

/**
 * Created by kam on 2018/3/18.
 */
@RequiredArgsConstructor
public class AiderBinder extends AbstractModule {
    @NonNull
    private Router router;
    
    @NonNull
    private WebAuth webAuth;

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
}
