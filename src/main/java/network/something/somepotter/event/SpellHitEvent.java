package network.something.somepotter.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.SpellEffect;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpellHitEvent<T extends Spell> extends SpellEvent<T> {

    public HitResult hitResult;

    @Cancelable
    public static class Pre<T extends Spell> extends SpellHitEvent<T> {
    }

    public static class Post<T extends Spell> extends SpellHitEvent<T> {
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

    public void addEffect(LivingEntity target, MobEffectInstance effectInstance) {
        target.addEffect(effectInstance);
        if (effectInstance.getEffect() instanceof SpellEffect<?> spellEffect) {
            spellEffect.onAdded(target, effectInstance.getAmplifier(), caster);
        }
    }

    @SubscribeEvent
    public static void onEffectExpiry(PotionEvent.PotionExpiryEvent event) {
        if (event.getPotionEffect() != null
                && event.getPotionEffect().getEffect() instanceof SpellEffect<?> effect) {

            effect.onExpired(event.getEntityLiving(), event.getPotionEffect().getAmplifier());
        }
    }

}
