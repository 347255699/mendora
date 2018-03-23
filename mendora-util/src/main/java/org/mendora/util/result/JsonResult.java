package org.mendora.util.result;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.mendora.util.constant.RetCode;
import org.mendora.util.constant.SysConst;

import java.util.Map;

/**
 * created by:xmf
 * date:2018/3/13
 * description:useful json result set
 */
public class JsonResult {
    public static JsonObject allocate(int initialCapacity) {
        return new JsonObject(MapResult.allocate(initialCapacity));
    }

    public static JsonObject succ() {
        return allocate(1).put(SysConst.SYS_RET_CODE, RetCode.SUCCESS.val());
    }

    public static JsonObject succ(Object payload) {
        return allocate(2)
                .put(SysConst.SYS_RET_CODE, RetCode.SUCCESS.val())
                .put(SysConst.SYS_DATA, payload);
    }

    public static JsonObject succWithRows(JsonArray rows) {
        JsonObject payload = allocate(2)
                .put(SysConst.SYS_SIZE, rows.size())
                .put(SysConst.SYS_ROWS, rows);
        return allocate(2)
                .put(SysConst.SYS_RET_CODE, RetCode.SUCCESS.val())
                .put(SysConst.SYS_DATA, payload);
    }

    public static JsonObject fail() {
        return allocate(1).put(SysConst.SYS_RET_CODE, RetCode.FAILURE.val());
    }

    public static JsonObject fail(Throwable err) {
        return allocate(2)
                .put(SysConst.SYS_RET_CODE, RetCode.FAILURE.val())
                .put(SysConst.SYS_ERR_MSG, err.getMessage());
    }

    public static JsonObject halfSucc() {
        return allocate(1).put(SysConst.SYS_RET_CODE, RetCode.HALF_SUCCESS.val());
    }

    public static JsonObject halfSucc(Throwable err) {
        return allocate(2)
                .put(SysConst.SYS_RET_CODE, RetCode.HALF_SUCCESS.val())
                .put(SysConst.SYS_ERR_MSG, err.getMessage());
    }

    public static JsonObject halfSucc(Object payload) {
        return allocate(2)
                .put(SysConst.SYS_RET_CODE, RetCode.HALF_SUCCESS.val())
                .put(SysConst.SYS_DATA, payload);
    }

    public static JsonObject halfSucc(Throwable err, Object payload) {
        return allocate(2)
                .put(SysConst.SYS_RET_CODE, RetCode.HALF_SUCCESS.val())
                .put(SysConst.SYS_ERR_MSG, err.getMessage())
                .put(SysConst.SYS_DATA, payload);
    }

    public static JsonObject empty(){
        return allocate(0);
    }

    /**
     * if status was success unzip data result,else return null value.
     *
     * @param result
     * @return
     */
    public static Object isSuccAndUnZip(JsonObject result) {
        Map<String, Object> temp = result.getMap();
        int retCode = result.getInteger(SysConst.SYS_RET_CODE);
        if (retCode == RetCode.SUCCESS.val())
            return temp.get(SysConst.SYS_DATA);
        else
            return null;
    }

    /**
     * if status was half success unzip data result,else return null value.
     *
     * @param result
     * @return
     */
    public static Object isHalfSuccAndUnZip(JsonObject result) {
        Map<String, Object> temp = result.getMap();
        int retCode = result.getInteger(SysConst.SYS_RET_CODE);
        if (retCode == RetCode.HALF_SUCCESS.val())
            return temp.get(SysConst.SYS_DATA);
        else
            return null;
    }

    /**
     * assert success status.
     *
     * @param result
     * @return
     */
    public static boolean isSucc(JsonObject result) {
        int retCode = result.getInteger(SysConst.SYS_RET_CODE);
        if (retCode == RetCode.SUCCESS.val())
            return true;
        else
            return false;
    }

    /**
     * assert half success status.
     *
     * @param result
     * @return
     */
    public static boolean isHalfSucc(JsonObject result) {
        int retCode = result.getInteger(SysConst.SYS_RET_CODE);
        if (retCode == RetCode.HALF_SUCCESS.val())
            return true;
        else
            return false;
    }

    /**
     * assert fail status.
     *
     * @param result
     * @return
     */
    public static boolean isFail(JsonObject result) {
        int retCode = result.getInteger(SysConst.SYS_RET_CODE);
        if (retCode == RetCode.FAILURE.val())
            return true;
        else
            return false;
    }
}
