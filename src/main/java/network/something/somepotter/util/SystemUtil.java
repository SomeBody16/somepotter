package network.something.somepotter.util;

import java.util.Map;

public class SystemUtil {

    public static void setEnv(String key, String value) {
        try {
            var env = System.getenv();
            Class<?> cl = env.getClass();
            var field = cl.getDeclaredField("m");
            field.setAccessible(true);
            var writableEnv = (Map<String, String>) field.get(env);
            writableEnv.put(key, value);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to set environment variable", e);
        }
    }

}
