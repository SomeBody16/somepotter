package network.something.somepotter.wand;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.init.KeyInit;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class WandListener {

    public static KeyMapping SPELL_CAST;

    public static void registerKeys() {
        SPELL_CAST = KeyInit.registerKey("spell_cast", "key.categories." + SomePotter.MOD_ID, InputConstants.KEY_GRAVE);
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) throws Exception {
        if (SPELL_CAST.isDown()) {
            SpeechToText.listen();
        }
    }

}
