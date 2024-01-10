package network.something.somepotter.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import network.something.somepotter.SomePotter;
import network.something.somepotter.spell.Spell;

public class SpellCastEvent<T extends Spell> extends SpellEvent<T> {

    @Cancelable
    public static class Pre<T extends Spell> extends SpellCastEvent<T> {
    }

    public static class Post<T extends Spell> extends SpellCastEvent<T> {
    }

    /**
     * Publish a SpellCastEvent Pre and Post
     *
     * @return true if cancelled
     */
    public static boolean publish(Spell spell, LivingEntity caster, ServerLevel level, int abilityPower, float areaOfEffect) {
        SomePotter.LOGGER.info("Casting {}, power-{}, aoe-{} | by {}", spell.getId(), abilityPower, areaOfEffect, caster.getDisplayName().getString());

        var spellCastEventPre = new SpellCastEvent.Pre<>();
        spellCastEventPre.spell = spell;
        spellCastEventPre.caster = caster;
        spellCastEventPre.level = level;
        spellCastEventPre.abilityPower = abilityPower;
        spellCastEventPre.areaOfEffect = areaOfEffect;

        var cancelled = MinecraftForge.EVENT_BUS.post(spellCastEventPre);
        if (cancelled) return true;

        var spellCastEventPost = new SpellCastEvent.Post<>();
        spellCastEventPost.spell = spellCastEventPre.spell;
        spellCastEventPost.caster = spellCastEventPre.caster;
        spellCastEventPost.level = spellCastEventPre.level;
        spellCastEventPost.abilityPower = spellCastEventPre.abilityPower;
        spellCastEventPost.areaOfEffect = spellCastEventPre.areaOfEffect;
        return MinecraftForge.EVENT_BUS.post(spellCastEventPost);
    }

}
