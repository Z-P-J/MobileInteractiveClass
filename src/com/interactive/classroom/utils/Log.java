package com.interactive.classroom.utils;

public final class Log {

    private Log() {
        throw new RuntimeException("forbiden");
    }

    public static void d(String tag, String msg) {
        System.out.println(tag + " : " + msg);
    }

//    public static void d(Class c, String msg) {
//        System.out.println(c.getName() + " : " + msg);
//    }

}
