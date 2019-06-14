package com.interactive.classroom.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Z-P-J
 */
public final class TimeUtil {

    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String currentDate() {
        return getFormatter().format(new Date());
    }

    public static String currentDate(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }

    private static SimpleDateFormat getFormatter() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    private TimeUtil() {

    }

}
