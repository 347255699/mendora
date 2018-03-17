package org.mendora.guice.properties;


import io.vertx.core.json.JsonObject;

import java.io.IOException;
import java.util.Map;

/**
 * Configuration for app.
 * Created by kam on 2017/12/3.
 */
public class ConfigHolder {
    // Hold properties body as map format.
    private static Map<String, Object> PROPERTIES;

    public static void init(String propPath) throws IOException {
        PROPERTIES = new PropertiesLoader(propPath).asMap();
    }

    /**
     * Get properties body.
     *
     * @return
     */
    public static Map<String, Object> properties() {
        return PROPERTIES;
    }

    /**
     * Get properties body as json.
     *
     * @return
     */
    public static JsonObject asJson() {
        return new JsonObject(PROPERTIES);
    }

    /**
     * Get single property from properties body.
     *
     * @param k
     * @return
     */
    public static String property(String k) {
        if (PROPERTIES != null && PROPERTIES.size() > 0)
            return asJson().getString(k);
        else throw new RuntimeException("The properties body have not any element.");
    }

    /**
     * Push single property into properties body.
     *
     * @param k
     * @return
     */
    public static void setProperty(String k, Object v) {
        PROPERTIES.put(k, v);
    }
}