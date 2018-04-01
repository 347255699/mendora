package org.mendora.guice.properties;

import io.vertx.core.json.JsonObject;

import java.util.Map;

/**
 * Configuration for app.
 * Created by kam on 2017/12/3.
 */
public class ConfigHolder {

    private Map<String, Object> prop;
    private String propPath;

    public ConfigHolder(PropertiesLoader popLoader) {
        this.prop = popLoader.asMap();
        this.propPath = popLoader.path();
    }

    /**
     * Get prop path
     *
     * @return
     */
    public String path() {
        return this.propPath;
    }

    /**
     * Get prop body.
     *
     * @return
     */
    public Map<String, Object> properties() {
        return this.prop;
    }

    /**
     * Get prop body as json.
     *
     * @return
     */
    public JsonObject asJson() {
        return new JsonObject(this.prop);
    }

    /**
     * Get single property from prop body.
     *
     * @param k
     * @return
     */
    public String property(String k) {
        if (prop != null && prop.size() > 0)
            return (String) this.prop.get(k);
        else throw new RuntimeException("The prop body have not any element.");
    }

    /**
     * Push single property into prop body.
     *
     * @param k
     * @return
     */
    public void setProperty(String k, Object v) {
        this.prop.put(k, v);
    }
}