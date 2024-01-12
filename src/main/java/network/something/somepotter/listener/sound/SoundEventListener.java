package network.something.somepotter.listener.sound;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.cast.projectile_or_self.ProjectileOrSelfCast;
import network.something.somepotter.cast.self.SelfCast;
import network.something.somepotter.cast.touch.TouchCast;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spell.Spell;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SoundEventListener {

    @SubscribeEvent
    public static void onSpellCast(SpellCastEvent.Post<Spell> event) {
        var cast = event.spell.getCast();

        if (cast instanceof SelfCast
                || (cast instanceof ProjectileOrSelfCast && event.caster.isCrouching())
                || (cast instanceof TouchCast)) {
            return;
        }

        event.spell.playCastSound(event);
    }

    @SubscribeEvent
    public static void onSpellHit(SpellHitEvent.Post<Spell> event) {
        event.spell.playHitSound(event);
    }

}
