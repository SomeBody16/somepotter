package network.something.somemagic.setup;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import network.something.somemagic.init.MessageInit;

public class ModSetup {

    public static void init(FMLCommonSetupEvent event) {
        MessageInit.register();
    }

}
