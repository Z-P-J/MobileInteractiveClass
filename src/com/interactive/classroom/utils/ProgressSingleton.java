package com.interactive.classroom.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 25714
 */
public class ProgressSingleton {

    private static final ConcurrentHashMap<String, Long> MAP = new ConcurrentHashMap<>();

    public static void put(String key, Long value){
        MAP.put(key, value);
    }

    public static Long get(String key){
        return MAP.get(key);
    }

    public static Long remove(String key){
        return MAP.remove(key);
    }

}
