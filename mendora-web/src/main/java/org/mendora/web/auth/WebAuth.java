package org.mendora.web.auth;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTOptions;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.auth.User;
import io.vertx.rxjava.ext.auth.jwt.JWTAuth;
import io.vertx.rxjava.ext.web.handler.AuthHandler;
import io.vertx.rxjava.ext.web.handler.JWTAuthHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.mendora.guice.properties.BaseConst;
import org.mendora.guice.properties.ConfigHolder;
import org.mendora.util.result.JsonResult;
import org.mendora.web.constant.WebConst;
import rx.Single;

import java.util.Set;

/**
 * created by:xmf
 * date:2018/3/22
 * description:
 */
@Slf4j
public class WebAuth {
    private String MODULE_NAME = "WEB-AUTH:";
    private JWTAuth jwtAuth;
    private Vertx vertx;
    private ConfigHolder configHolder;
    private static final String JWT_ALG_HS512 = "HS512";

    public WebAuth(Vertx vertx, ConfigHolder configHolder) {
        this.vertx = vertx;
        this.configHolder = configHolder;
        initJWTAuth();
    }

    /**
     * get jwt auth.
     *
     * @return
     */
    public JWTAuth jwtAuth() {
        return jwtAuth;
    }

    /**
     * initialization jwt auth.
     */
    @SneakyThrows
    private void initJWTAuth() {
        JsonObject config = new JsonObject().put("keyStore", new JsonObject()
                .put("path", configHolder.property(BaseConst.BASE_ROOT_PATH) + "/config/keystore.jceks")
                .put("type", "jceks")
                .put("password", configHolder.property(WebConst.WEB_JWT_KEY_PASSWD)));
        jwtAuth = JWTAuth.create(vertx, config);
    }

    /**
     * issue jwt token.
     *
     * @param payload
     * @return
     */
    public String issueJWToken(JsonObject payload) {
        JWTOptions options = new JWTOptions()
                .setAlgorithm(JWT_ALG_HS512)
                .setIssuer(configHolder.property(WebConst.WEB_JWT_ISSUER))
                .setExpiresInMinutes(Long.parseLong(configHolder.property(WebConst.WEB_JWT_EXPIRES_MINUTES)));
        return jwtAuth.generateToken(payload, options);
    }

    /**
     * binding auth handler with a permission flag.
     *
     * @param permissionFlag
     * @return
     */
    public AuthHandler createAuthHandler(String permissionFlag) {
        return JWTAuthHandler.create(jwtAuth)
                .addAuthority(permissionFlag);
    }

    /**
     * binding auth handler with group permission flag.
     *
     * @param permissionFlags
     * @return
     */
    public AuthHandler createAuthHandler(Set<String> permissionFlags) {
        return JWTAuthHandler.create(jwtAuth)
                .addAuthorities(permissionFlags);
    }

    /**
     * authenticate jwt token.
     *
     * @param jwToken
     * @return
     */
    public Single<User> rxAuthenticateJWToken(String jwToken) {
        return jwtAuth.rxAuthenticate(JsonResult.one()
                .put("jwt", jwToken));
    }

    /**
     * authenticate jwt token.
     *
     * @param jwToken
     * @param options
     * @return
     */
    public Single<User> rxAuthenticateJWToken(String jwToken, JsonObject options) {
        JsonObject doc = JsonResult.two()
                .put("jwt", jwToken)
                .put("options", options);
        return jwtAuth.rxAuthenticate(doc);
    }

}
