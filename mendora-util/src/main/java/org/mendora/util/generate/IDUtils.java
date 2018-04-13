package org.mendora.util.generate;

import java.util.UUID;

/**
 * Created by kam on 2018/4/13.
 */
public class IDUtils {
    public static String uuid() {
        return UUID.randomUUID().toString();
    }
}
