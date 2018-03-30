package org.mendora.web.route.user;

import com.google.inject.Inject;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;
import lombok.val;
import org.mendora.guice.scanner.route.AbstractRoute;
import org.mendora.guice.scanner.route.RequestRouting;
import org.mendora.guice.scanner.route.Route;
import org.mendora.util.result.JsonResult;
import org.mendora.util.result.WebResult;
import org.mendora.web.auth.WebAuth;

/**
 * Created by kam on 2018/3/30.
 */
@Route("/mendora/user")
public class UserRoute extends AbstractRoute {
    public static final int NOT_PERMESSIONS = -3;
    @Inject
    private WebAuth webAuth;

    @RequestRouting(path = "/login", method = HttpMethod.POST)
    public void login(RoutingContext rc) {
        val usr = "root";
        val passwd = "123";
        JsonObject doc = rc.getBodyAsJson();
        doc.put("permissions", "normal");
        if (usr.equals(doc.getString("usr")) && passwd.equals(doc.getString("passwd"))) {
            WebResult.consume(JsonResult.succ(webAuth.issueJWToken(doc)), rc);
        }
    }

    @RequestRouting(path = "/redirect", method = HttpMethod.GET)
    public void redirect(RoutingContext rc) {
        JsonObject payload = JsonResult.two()
                .put("suggestion", "")
                .put("msg", "Sorry, you have not permessions to access.");
        WebResult.consume(payload, NOT_PERMESSIONS, rc);
    }
}
