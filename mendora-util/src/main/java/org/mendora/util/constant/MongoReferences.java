package org.mendora.util.constant;

import io.vertx.core.json.JsonObject;
import org.mendora.util.result.JsonResult;

public enum MongoReferences {
    COLLECTION("collection"), DOCUMENT("document"),
    FIND_OPTIONS("findOptions"), UPDATE_OPTIONS("updateOptions"),
    FIELDS("fields"), QUERY("query"), COMMAND_NAME("commandName"),
    COMMAND("command");
    private String val;

    MongoReferences(String val) {
        this.val = val;
    }

    public String str(JsonObject params) {
        return params.containsKey(val) ? params.getString(val) : null;
    }

    public JsonObject json(JsonObject params) {
        return params.containsKey(val) ? params.getJsonObject(val) : JsonResult.empty();
    }

    public String val() {
        return val;
    }
}
