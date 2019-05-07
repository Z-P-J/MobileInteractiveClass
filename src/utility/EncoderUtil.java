package utility;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncoderUtil {

    public static String getStringEncoderByAgent(String fileName, String agent){
        String filenameEncoder = "";
        try {
            if (agent.contains("MSIE")) {
                //IE编码
                filenameEncoder = URLEncoder.encode(fileName, "utf-8");
                filenameEncoder.replace("+", " ");
            } else if (agent.contains("FireFox")) {
                filenameEncoder = "=?utf-8?B?" + Base64.getEncoder().encodeToString(fileName.getBytes(StandardCharsets.UTF_8)) + "?=";
            } else {
                filenameEncoder = URLEncoder.encode(fileName, "utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filenameEncoder;
    }
}
