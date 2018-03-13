package org.mendora.data.accesser;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.ResultSet;
import io.vertx.rxjava.ext.asyncsql.AsyncSQLClient;
import io.vertx.rxjava.ext.sql.SQLConnection;
import org.mendora.data.client.ClientHolder;
import org.mendora.util.result.MapResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Single;

import java.util.List;
import java.util.Map;

/**
 * Created by kam on 2018/3/12.
 */
public class DataAccesser {
    private static final String MODULE_NAME = "DATA_ACCESSER:";
    private static final String CONN_TEMP_KEY = "connTemp";
    private static Logger logger = LoggerFactory.getLogger(DataAccesser.class);

    /**
     * release sql connection come home(connection pool).
     * @param single
     * @param temp
     * @param <T>
     * @return
     */
    private static <T> Single<T> close(Single<T> single, Map<String, SQLConnection> temp) {
        return single.doOnError(err -> {
            if (temp.get(CONN_TEMP_KEY) != null) {
                temp.get(CONN_TEMP_KEY).close();
                logger.info(MODULE_NAME + "connection closed.");
            }
        }).doOnSuccess(t -> {
            if (temp.get(CONN_TEMP_KEY) != null) {
                temp.get(CONN_TEMP_KEY).close();
                logger.info(MODULE_NAME + "connection closed.");
            }
        });
    }

    /**
     * query find sql statement without params.
     *
     * @param sql
     * @return
     */
    public static Single<List<JsonObject>> rxQuery(String sql) {
        logger.info(MODULE_NAME + sql);
        Map<String, SQLConnection> temp = MapResult.allocate(1);
        AsyncSQLClient postgre = ClientHolder.postgre();
        if (postgre == null)
            return Single.error(new RuntimeException("the postgre Client not ready,please wait a moment and try again."));
        Single<List<JsonObject>> single = postgre.rxGetConnection()
                .flatMap(conn -> {
                    temp.put(CONN_TEMP_KEY, conn);
                    return conn.rxQuery(sql);
                })
                .map(ResultSet::getRows);
        return close(single, temp);
    }
}
