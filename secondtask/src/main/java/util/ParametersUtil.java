package util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ParametersUtil {
    public static final String API_KEY;
    private static final String CONFIG_PATH = "src/main/resources/configuration.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        load();
        API_KEY = PROPERTIES.getProperty("apiKey");
    }

    private static void load() {
        try (FileInputStream fileInputStream = new FileInputStream(ParametersUtil.CONFIG_PATH)) {
            PROPERTIES.load(fileInputStream);
        } catch (IOException ex) {
            log.error(ex.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
