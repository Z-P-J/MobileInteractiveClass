package utility;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 25714
 */
public final class TimeUtil {

    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String currentDate() {
        synchronized (FORMATTER) {
            return FORMATTER.format(new Date());
        }
    }

    public static String currentDate(String pattern) {
        synchronized (FORMATTER) {
            return new SimpleDateFormat(pattern).format(new Date());
        }
    }

    private TimeUtil() {

    }

}
