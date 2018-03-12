package org.mendora.data.accesser;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.ResultSet;
import io.vertx.rxjava.ext.sql.SQLConnection;
import org.mendora.data.client.ClientHolder;
import org.mendora.util.constant.MapResult;
import rx.Single;

import java.util.List;
import java.util.Map;

/**
 * Created by kam on 2018/3/12.
 */
public class DataAccesser {
    private static final String CONN_TEMP_KEY = "connTemp";

    private static <T> Single<T> close(Single<T> single, Map<String, SQLConnection> temp) {
        return single.doOnError(err -> {
            if (temp.get(CONN_TEMP_KEY) != null) temp.get(CONN_TEMP_KEY).close();
        }).doOnSuccess(t -> {
            if (temp.get(CONN_TEMP_KEY) != null) temp.get(CONN_TEMP_KEY).close();
        });
    }

    /**
     * query find sql statement without params.
     *
     * @param sql
     * @return
     */
    public static Single<List<JsonObject>> rxQuery(String sql) {
        Map<String, SQLConnection> temp = MapResult.allocate(1);
        Single<List<JsonObject>> single = ClientHolder.postgre().rxGetConnection()
                .flatMap(conn -> {
                    temp.put(CONN_TEMP_KEY, conn);
                    return conn.rxQuery(sql);
                })
                .map(ResultSet::getRows);
        return close(single, temp);
    }
}
