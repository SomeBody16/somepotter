package network.something.somepotter.util;

import net.minecraftforge.fml.ModList;

public class IntegrationUtil {

    public static void theVault(Runnable callback) {
        if (ModList.get().isLoaded("the_vault")) {
            callback.run();
        }
    }

}
