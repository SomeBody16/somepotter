package network.something.somepotter.listener.sound;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spell.Spell;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SoundEventListener {

    @SubscribeEvent
    public static void onSpellCast(SpellCastEvent.Post<Spell> event) {
        event.spell.playCastSound(event);
    }

    @SubscribeEvent
    public static void onSpellHit(SpellHitEvent.Post<Spell> event) {
        event.spell.playHitSound(event);
    }

}
