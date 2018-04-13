package org.mendora.util.generate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by kam on 2018/4/13.
 */
public class DateUtils {
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss:mm";

    public static String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DEFAULT_PATTERN));
    }

    public static String now(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }
}
