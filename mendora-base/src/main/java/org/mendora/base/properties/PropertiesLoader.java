package org.mendora.base.properties;

import java.io.*;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Loader for properties file.
 * Created by kam on 2017/12/3.
 */
public class PropertiesLoader {
    // private final static String DEFAULT_PATH = "config/config.properties";
    // Hold the properties body.
    private Properties prop;
    // Hold the properties path blow classpath.
    private String propPath;

    public PropertiesLoader(String propPath) throws IOException {
        this.prop = new Properties();
        this.propPath = propPath;
        prop.load(new FileInputStream(new File(propPath)));
    }

    /**
     * Get properties path blow classpath.
     *
     * @return
     */
    public String path() {
        return this.propPath;
    }

    /**
     * Get properties body.
     *
     * @return
     */
    public Properties properties() {
        return this.prop;
    }

    /**
     * Get the single property with a key from properties body.
     *
     * @param k
     * @return
     */
    public String property(String k) {
        return this.prop.getProperty(k);
    }

    /**
     * Get properties body as map format.
     *
     * @return
     */
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>(this.prop.size(), 1.0f);
        this.prop.keySet().forEach(obj -> {
            String k = (String) obj;
            map.put(k, this.prop.get(k));
        });
        return map;
    }
}
