package com.interactive.classroom.utils;

/**
 * @author Z-P-J
 */
public final class TextUtil {

    private TextUtil() { };

    /**
     * 检查字符串是否为空
     * @param str 待检查的字符串
     * @return boolean 字符串是否为空
     */
    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }
        return str.isEmpty();
    }

}
