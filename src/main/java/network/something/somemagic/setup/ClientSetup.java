package network.something.somemagic.setup;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import network.something.somemagic.SomeMagic;
import network.something.somemagic.client.KeyInit;
import network.something.somemagic.client.renderer.SpellRenderer;
import network.something.somemagic.client.speech.SpeechToSpellThread;
import network.something.somemagic.init.EntityInit;

@Mod.EventBusSubscriber(modid = SomeMagic.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(EntityInit.SPELL.get(), SpellRenderer::new);
        KeyInit.register();
        SpeechToSpellThread.getInstance().start();
    }
}
