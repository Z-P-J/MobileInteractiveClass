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

    private TimeUtil() {

    }

}
