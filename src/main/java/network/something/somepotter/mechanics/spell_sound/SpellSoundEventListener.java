package network.something.somepotter.mechanics.spell_sound;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spells.cast.projectile_or_self.ProjectileOrSelfCast;
import network.something.somepotter.spells.cast.self.SelfCast;
import network.something.somepotter.spells.cast.touch.TouchCast;
import network.something.somepotter.spells.spell.Spell;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpellSoundEventListener {

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
