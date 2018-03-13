package org.mendora.util.result;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kam on 2018/3/12.
 * useful map result set
 */
public class MapResult {
    private static final float DEFAULT_LOAD_FACTOR = 1.0F;

    public static <T> Map<String, T> allocate(int initialCapacity) {
        return new HashMap<>(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

}
