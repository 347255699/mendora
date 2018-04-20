package org.mendora.web.route.aider;

import com.google.inject.Inject;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.mendora.guice.scanner.route.AbstractRoute;
import org.mendora.guice.scanner.route.RequestRouting;
import org.mendora.guice.scanner.route.Route;
import org.mendora.service.facade.dataAccesser.postgre.rxjava.PostgreAccesser;
import org.mendora.util.constant.SqlReferences;
import org.mendora.web.aop.logger.RouteLogging;
import org.mendora.web.efficiency.result.WebResult;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
@Slf4j
@Route("/mendora/aider")
public class DemoRoute extends AbstractRoute {
    @Inject
    private PostgreAccesser postgreAccesser;
    @Inject
    private WebResult webResult;

    @RequestRouting(path = "/demo", method = HttpMethod.GET)
    public void demo(RoutingContext rc) {
        rc.response().end("<h1>Just a test demo.</h1>");
    }

    @RequestRouting(path = "/config", method = HttpMethod.GET)
    public void config(RoutingContext rc) {
        webResult.succ(configHolder.asJson(), rc);
    }

    @RouteLogging("分页查询")
    @RequestRouting(path = "/sqlStatement/query", method = HttpMethod.POST)
    public void query(RoutingContext rc) {
        JsonObject user = rc.user().principal();
        log.info(user.toString());
        postgreAccesser
                .rxQuery(rc.getBodyAsJson().getString(SqlReferences.STATEMENT.val()))
                .subscribe(replyJson -> webResult.consume(replyJson, rc));
    }

    @RouteLogging("formData调试")
    @RequestRouting(path = "/formData", method = HttpMethod.POST)
    public void formData(RoutingContext rc){
        webResult.succ(rc);
    }

}
