package network.something.somemagic.setup;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import network.something.somemagic.SomeMagic;
import network.something.somemagic.init.MessageInit;

@Mod.EventBusSubscriber(modid = SomeMagic.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSetup {

    public static void init(FMLCommonSetupEvent event) {
        MessageInit.register();
    }

}
