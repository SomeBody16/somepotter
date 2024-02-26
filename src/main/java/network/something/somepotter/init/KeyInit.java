package network.something.somepotter.init;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import network.something.somepotter.SomePotter;
import network.something.somepotter.mechanics.gesture.GestureListener;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class KeyInit {

    public static final String CATEGORY = "key.categories." + SomePotter.MOD_ID;

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        GestureListener.registerKeys();
    }

}
