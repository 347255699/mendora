package org.mendora.util.constant;

import io.vertx.core.json.JsonObject;
import org.mendora.util.result.JsonResult;

/**
 * Created by kam on 2018/3/26.
 */
public enum PageReferences {
    SORT_BY("sortBy"), CURR_PAGE("currPage"), SIZE("size"),TOTAL_SIZE("totalSize"),
    ROWS("rows");
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
        return params.containsKey(val)?params.getJsonObject(val): JsonResult.empty();
    }

    public int number() {
        return Integer.parseInt(val);
    }
    public int number(JsonObject params) {
        return params.getInteger(val);
    }
}
