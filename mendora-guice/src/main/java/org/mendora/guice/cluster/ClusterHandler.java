package org.mendora.guice.cluster;

import com.google.inject.Injector;

/**
 * created by:xmf
 * date:2017/10/31
 * description:
 */
@FunctionalInterface
public interface ClusterHandler {
    void handle(Injector injector);
}
