package network.something.somepotter.setup;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import network.something.somepotter.init.MessageInit;

public class ModSetup {

    public static void init(FMLCommonSetupEvent event) {
        MessageInit.register();
    }

}
