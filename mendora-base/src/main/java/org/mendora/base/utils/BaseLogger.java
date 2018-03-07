package org.mendora.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
public class BaseLogger {
    private static Logger logger;

    public static void init() {
        logger = LoggerFactory.getLogger(BaseLogger.class);
    }

    public static void info(String msg) {
        logger.info(msg);
    }

    public static void info(String msg, Object... objects) {
        logger.info(msg, objects);
    }

    public static void debug(String msg, Object... objects) {
        logger.debug(msg, objects);
    }

    public static void debug(String msg) {
        logger.debug(msg);
    }

    public static void warn(String msg, Object... objects) {
        logger.warn(msg, objects);
    }

    public static void warn(String msg) {
        logger.warn(msg);
    }

    public static void error(String msg, Object... objects) {
        logger.error(msg, objects);
    }

    public static void error(String msg) {
        logger.error(msg);
    }

    public static void error(String msg, Throwable throwable) {
        logger.error(msg, throwable);
    }

    public static org.slf4j.Logger self() {
        return logger;
    }
}
