package org.mendora.util.constant;

import io.vertx.core.json.JsonObject;

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
        return params.getString(val);
    }

    public JsonObject json(JsonObject params) {
        return params.getJsonObject(val);
    }

    public String val() {
        return val;
    }
}
