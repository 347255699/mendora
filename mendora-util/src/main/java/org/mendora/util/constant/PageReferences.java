package org.mendora.util.constant;

import io.vertx.core.json.JsonObject;

/**
 * Created by kam on 2018/3/26.
 */
public enum PageReferences {
    SORT_BY("sortBy"), CURR_PAGE("currPage"), SIZE("size");
    private String val;

    PageReferences(String val) {
        this.val = val;
    }

    public String val() {
        return val;
    }

    public String str(JsonObject params) {
        return params.getString(val);
    }

    public JsonObject json(JsonObject params) {
        return params.getJsonObject(val);
    }

    public int number() {
        return Integer.parseInt(val);
    }
    public int number(JsonObject params) {
        return params.getInteger(val);
    }
}
