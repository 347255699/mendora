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

    @RequestRouting(path = "", method = HttpMethod.GET)
    public void root(){
        //vertx.sharedData().rxGetClusterWideMap(SALT_KEY)
          //      .subscribe(asyncMap -> asyncMap.put())
    }

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

}
