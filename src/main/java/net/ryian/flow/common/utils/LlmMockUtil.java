package net.ryian.flow.common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;


/**
 * @author allenwc
 * @date 2024/5/13 20:42
 */
public class LlmMockUtil {

    private LlmMockUtil() {
    }

    public static String getResourceFileAsString(String fileName) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(fileName)) {
            if (is == null) return null;
            try (InputStreamReader isr = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not read from resource file " + fileName, e);
        }
    }

    public static String get(String nodeId) {
        return getResourceFileAsString("mock/"+nodeId);
    }

}
