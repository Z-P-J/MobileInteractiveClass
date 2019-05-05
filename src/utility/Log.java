package utility;

public final class Log {

    private Log() {
        throw new RuntimeException("forbiden");
    }

    public static void d(String title, String msg) {
        System.out.println(title + " : " + msg);
    }

//    public static void d(Class c, String msg) {
//        System.out.println(c.getName() + " : " + msg);
//    }

}
