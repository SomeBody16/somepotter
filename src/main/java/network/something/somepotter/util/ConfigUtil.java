package network.something.somepotter.util;

import ca.lukegrahamlandry.lib.config.ConfigWrapper;
import network.something.somepotter.SomePotter;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ConfigUtil {

    public static <T> Supplier<T> server(@NotNull Class<T> clazz, String name) {
        return ConfigWrapper
                .server(clazz)
                .dir(SomePotter.MOD_ID)
                .named(name);
    }

    public static <T> Supplier<T> client(@NotNull Class<T> clazz, String name) {
        return ConfigWrapper
                .client(clazz)
                .dir(SomePotter.MOD_ID)
                .named(name);
    }

}
