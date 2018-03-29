package org.mendora.service.facade.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * created by:xmf
 * date:2018/3/27
 * description:
 */
public class MonitorMethodInterceptor implements org.aopalliance.intercept.MethodInterceptor {
    private Logger log = LoggerFactory.getLogger(MonitorMethodInterceptor.class);
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        before(methodInvocation);
        Object object = methodInvocation.proceed();
        after(methodInvocation);
        return object;
    }

    /**
     * before service method execute.
     *
     * @param methodInvocation
     */
    private void before(MethodInvocation methodInvocation) {
        // before service method execute.
        String name = methodInvocation.getMethod().getName();
        log.info(name);
    }

    /**
     * after service method execute.
     *
     * @param methodInvocation
     */
    private void after(MethodInvocation methodInvocation) {
        // after service method execute.
        String name = methodInvocation.getMethod().getName();
        log.info(name);
    }
}
