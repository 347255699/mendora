package org.mendora.web.route.app;

import com.google.inject.Inject;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;
import lombok.val;
import org.mendora.guice.scanner.route.AbstractRoute;
import org.mendora.guice.scanner.route.RequestRouting;
import org.mendora.guice.scanner.route.Route;
import org.mendora.service.facade.dataAccesser.mongo.rxjava.MongoAccesser;
import org.mendora.util.constant.MongoCol;
import org.mendora.util.generate.IDUtils;
import org.mendora.util.generate.MongoAccesserUtils;
import org.mendora.util.result.JsonResult;
import org.mendora.web.auth.WebAuth;
import org.mendora.web.efficiency.result.WebResult;

/**
 * Created by kam on 2018/4/16.
 */
@Route("/mendora/app")
public class AppRoute extends AbstractRoute {
    private static final String SALT_KEY = "salt.key";
    @Inject
    private WebAuth webAuth;
    @Inject
    private WebResult webResult;
    @Inject
    private MongoAccesser mongoAccesser;

    @RequestRouting(path = "/login", method = HttpMethod.POST)
    public void login(RoutingContext rc) {
        val usr = "root";
        val passwd = "123";
        JsonObject doc = rc.getBodyAsJson();
        doc.put("permissions", new JsonArray().add("role:normal"));
        if (usr.equals(doc.getString("username")) && passwd.equals(doc.getString("password"))) {
            webResult.consume(JsonResult.succ(webAuth.issueJWToken(doc)), rc);
        }
    }

    @RequestRouting(path = "/register", method = HttpMethod.POST)
    public void register(RoutingContext rc) {
        JsonObject user = rc.getBodyAsJson()
                .put("_id", IDUtils.uuid());
        mongoAccesser.rxSave(MongoAccesserUtils.save(MongoCol.COL_USER, user))
                .subscribe(id -> webResult.consume(JsonResult.succ(id), rc));
    }

}
