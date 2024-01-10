package network.something.somepotter.util;

import net.minecraftforge.fml.ModList;

public class IntegrationUtil {

    public static boolean theVault() {
        return ModList.get().isLoaded("the_vault");
    }

    public static void theVault(Runnable callback) {
        if (theVault()) {
            callback.run();
        }
    }

}
