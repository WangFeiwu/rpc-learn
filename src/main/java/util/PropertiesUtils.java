package util;

import java.io.IOException;
import java.util.Properties;

/**
 * @Author wfw
 * @Date 2020/06/16 19:27
 */
public class PropertiesUtils {

    private static Properties properties;

    static {
        try {
            properties = new Properties();
            properties.load(Properties.class.getResourceAsStream("/app.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperties(String key) {
        return properties.getProperty(key);
    }
}
