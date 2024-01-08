package network.something.somepotter.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import network.something.somepotter.spell.Spell;

public class SpellHitEvent<T extends Spell> extends SpellEvent<T> {

    public HitResult hitResult;

    private SpellHitEvent() {
    }

    @Cancelable
    public static class Pre<T extends Spell> extends SpellHitEvent<T> {
        private Pre() {
        }
    }

    public static class Post<T extends Spell> extends SpellHitEvent<T> {
        private Post() {
        }
    }

    /**
     * Publish a SpellHitEvent Pre and Post
     *
     * @return true if cancelled
     */
    public static boolean publish(Spell spell, LivingEntity caster, ServerLevel level, int abilityPower, float areaOfEffect, HitResult hitResult) {
        var spellHitEventPre = new SpellHitEvent.Pre<>();
        spellHitEventPre.spell = spell;
        spellHitEventPre.caster = caster;
        spellHitEventPre.level = level;
        spellHitEventPre.abilityPower = abilityPower;
        spellHitEventPre.areaOfEffect = areaOfEffect;
        spellHitEventPre.hitResult = hitResult;

        var cancelled = MinecraftForge.EVENT_BUS.post(spellHitEventPre);
        if (cancelled) return true;

        var spellHitEventPost = new SpellHitEvent.Post<>();
        spellHitEventPost.spell = spellHitEventPre.spell;
        spellHitEventPost.caster = spellHitEventPre.caster;
        spellHitEventPost.level = spellHitEventPre.level;
        spellHitEventPost.abilityPower = spellHitEventPre.abilityPower;
        spellHitEventPost.areaOfEffect = spellHitEventPre.areaOfEffect;
        spellHitEventPost.hitResult = spellHitEventPre.hitResult;
        return MinecraftForge.EVENT_BUS.post(spellHitEventPost);
    }

}
