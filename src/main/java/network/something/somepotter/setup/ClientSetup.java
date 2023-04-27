package network.something.somepotter.setup;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import network.something.somepotter.SomePotter;
import network.something.somepotter.client.KeyInit;
import network.something.somepotter.client.renderer.SpellRenderer;
import network.something.somepotter.client.speech.SpeechToSpellThread;
import network.something.somepotter.init.EntityInit;
import network.something.somepotter.spell.cast.missile.SpellMissileRenderer;
import network.something.somepotter.spell.spells.morsmordre.dark_mark.DarkMarkRenderer;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        KeyInit.register();
        SpeechToSpellThread.getInstance().start();
        entityRenderers();
    }

    private static void entityRenderers() {
        // Spell casts
        EntityRenderers.register(EntityInit.SPELL_PROJECTILE.get(), SpellRenderer::new);
        EntityRenderers.register(EntityInit.SPELL_TOUCH.get(), SpellRenderer::new);
        EntityRenderers.register(EntityInit.SPELL_MISSILE.get(), SpellMissileRenderer::new);

        // Spell entities
        EntityRenderers.register(EntityInit.DARK_MARK.get(), DarkMarkRenderer::new);
    }
}
