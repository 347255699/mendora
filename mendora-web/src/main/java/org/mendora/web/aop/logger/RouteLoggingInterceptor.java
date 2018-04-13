package org.mendora.web.aop.logger;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.MultiMap;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.mendora.util.generate.DateUtils;
import org.mendora.util.generate.IDUtils;
import org.mendora.util.result.JsonResult;
import org.mendora.web.constant.WebConst;

/**
 * Created by kam on 2018/4/13.
 */
public class RouteLoggingInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        RoutingContext rc = (RoutingContext) methodInvocation.getArguments()[0];

        if (rc.user() != null) {
            String desc = methodInvocation.getMethod().getAnnotation(RouteLogging.class).value();
            MultiMap headersMap = rc.request().headers();
            JsonObject headers = JsonResult.allocate(headersMap.size());
            headersMap.names().forEach(name -> headers.put(name, headersMap.get(name)));
            HttpMethod method = rc.request().method();
            JsonObject logging = JsonResult.allocate(11)
                    .put("_id", IDUtils.uuid())
                    .put("username", rc.user().principal().getString("username"))
                    .put("date", DateUtils.now())
                    .put("uri", rc.request().absoluteURI())
                    .put("desc", desc)
                    .put("method", method.name())
                    .put("headers", headers)
                    .put("jwt", rc.user().principal());
            String contentType = headers.getString("Content-Type");
            if ((HttpMethod.PUT == method || HttpMethod.POST == method || HttpMethod.PATCH == method) &&
                    !contentType.contains("form-data")) {
                logging.put("body", rc.getBodyAsJson());
            }
            if (contentType.contains("form-data")) {
                MultiMap formDataMap = rc.request().formAttributes();
                JsonObject formData = JsonResult.allocate(formDataMap.size());
                formDataMap.names().forEach(name -> formData.put(name, formDataMap.get(name)));
                logging.put("formData", formData);
            }
            rc.put(WebConst.WEB_ROUTE_lOGGING_KEY, logging);
        }
        methodInvocation.proceed();
        return null;
    }
}
