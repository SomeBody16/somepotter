package network.something.somepotter.spell.api.particle;

import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SomePotter.MOD_ID)
public class ParticleListener {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void handle(SpellHitBlockEvent event) {
        var spellId = event.getSpellId();
        var level = event.getCaster().level;
        var pos = event.getLocation();

        if (!level.isClientSide) {
            SpellParticle.playTouchParticles(spellId, (ServerLevel) level, pos);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void handle(SpellHitEntityEvent event) {
        var spellId = event.getSpellId();
        var level = event.getCaster().level;
        var pos = event.getLocation();

        if (!level.isClientSide) {
            SpellParticle.playTouchParticles(spellId, (ServerLevel) level, pos);
        }
    }
}
