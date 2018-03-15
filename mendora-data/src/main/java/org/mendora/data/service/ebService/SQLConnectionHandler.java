package org.mendora.data.service.ebService;

import io.vertx.rxjava.ext.sql.SQLConnection;

/**
 * created by:xmf
 * date:2017/10/31
 * description:
 */
@FunctionalInterface
public interface SQLConnectionHandler {
    void handle(SQLConnection conn);
}
