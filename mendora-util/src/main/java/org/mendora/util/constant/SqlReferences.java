package org.mendora.util.constant;

import io.vertx.core.json.JsonObject;
import org.mendora.util.result.JsonResult;

/**
 * about sql element name;
 */
public enum SqlReferences {
    STATEMENT("statement"), PARAMS("params"), BATCH("batch");
    private String val;

    SqlReferences(String val) {
        this.val = val;
    }

    public String val() {
        return this.val;
    }

    public String str(JsonObject params) {
        return params.containsKey(val) ? params.getString(val) : null;
    }

    public JsonObject json(JsonObject params) {
        return params.containsKey(val) ? params.getJsonObject(val) : JsonResult.empty();
    }
}
