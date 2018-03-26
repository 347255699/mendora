package org.mendora.web.route.aider;

import com.google.inject.Inject;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.mendora.guice.scanner.route.AbstractRoute;
import org.mendora.guice.scanner.route.RequestRouting;
import org.mendora.guice.scanner.route.Route;
import org.mendora.service.facade.dataAccesser.rxjava.DataAccessService;
import org.mendora.util.constant.SqlReferences;
import org.mendora.util.result.JsonResult;
import org.mendora.util.result.WebResult;
import org.mendora.web.auth.WebAuth;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
@Slf4j
@Route("/mendora/aider")
public class DemoRoute extends AbstractRoute {
    @Inject
    private DataAccessService dataAccessService;
    @Inject
    private WebAuth webAuth;
    @RequestRouting(path = "/demo", method = HttpMethod.GET)
    public void demo(RoutingContext rc) {
        rc.response().end("<h1>Just a test demo.</h1>");
    }

    @RequestRouting(path = "/config", method = HttpMethod.GET)
    public void config(RoutingContext rc) {
        WebResult.succ(configHolder.asJson(), rc);
    }

    @RequestRouting(path = "/sqlStatement/query", method = HttpMethod.POST)
    public void query(RoutingContext rc) {
        dataAccessService
                .rxQuery(rc.getBodyAsJson().getString(SqlReferences.STATEMENT.val()))
                .subscribe(replyJson -> WebResult.consume(replyJson, rc));
    }

    @Override
    public void route(String prefix) {
        val usr = "root";
        val passwd = "123";
        router.get(prefix + "/demo2").handler(rc -> {
            WebResult.consume(JsonResult.empty(), "<h1>Just a test demo2.</h1>", rc);
        });
        router.post(prefix + "/login").handler(rc -> {
            JsonObject doc = rc.getBodyAsJson();
            if (usr.equals(doc.getString("usr")) && passwd.equals(doc.getString("passwd"))){
                WebResult.consume(JsonResult.succ(webAuth.issueJWToken(doc)), rc);
            }
        });
    }
}
