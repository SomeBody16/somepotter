package network.something.somepotter.spells.spell.disillusio;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.init.EffectInit;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.util.AbilityPowerUtil;

public class DisillusioListener extends SpellListener<DisillusioSpell> {

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<DisillusioSpell> event, EntityHitResult hitResult, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {

            var duration = AbilityPowerUtil.scale(event.abilityPower, 20 * 10, 20 * 60 * 2);
            var disillusio = new MobEffectInstance(EffectInit.DISILLUSIO.get(), duration, 0, false, false, true);
            var invisibility = new MobEffectInstance(MobEffects.INVISIBILITY, duration, 0, false, false, false);

            event.addEffect(livingEntity, disillusio);
            event.addEffect(livingEntity, invisibility);
        }
    }
}
