package network.something.somepotter.init;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import network.something.somepotter.SomePotter;
import network.something.somepotter.mechanics.gesture.GestureListener;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class KeyInit {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        GestureListener.registerKeys();
    }

    public static KeyMapping registerKey(String name, String category, int keyCode) {
        final var key = new KeyMapping("key." + SomePotter.MOD_ID + "." + name, keyCode, category);
        ClientRegistry.registerKeyBinding(key);
        return key;
    }

}
