package org.mendora.util.generate;

import io.vertx.core.json.JsonObject;
import org.mendora.util.constant.MongoReferences;
import org.mendora.util.result.JsonResult;

/**
 * Created by kam on 2018/4/13.
 */
public class MongoAccesserUtils {
    public static JsonObject save(String colName, JsonObject doc) {
        return JsonResult.two()
                .put(MongoReferences.COLLECTION.val(), colName)
                .put(MongoReferences.DOCUMENT.val(), doc);
    }

    public static JsonObject findOne(String colName, JsonObject query) {
        return JsonResult.two()
                .put(MongoReferences.COLLECTION.val(), colName)
                .put(MongoReferences.QUERY.val(), query);
    }

    public static JsonObject findOne(String colName, JsonObject query, JsonObject fields) {
        return JsonResult.two()
                .put(MongoReferences.COLLECTION.val(), colName)
                .put(MongoReferences.QUERY.val(), query)
                .put(MongoReferences.FIELDS.val(), fields);
    }
}
