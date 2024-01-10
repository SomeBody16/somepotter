package network.something.somepotter.spell.arresto_momentum;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.cast.projectile.ProjectileCast;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.util.AbilityPowerUtil;

public class ArrestoMomentumListener extends SpellListener<ArrestoMomentumSpell> {

    @Override
    public void onSpellCast(SpellCastEvent.Post<ArrestoMomentumSpell> event) {
        new ProjectileCast(event).execute();
    }

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<ArrestoMomentumSpell> event, EntityHitResult hitResult, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {

            var duration = AbilityPowerUtil.scale(event.abilityPower, 20 * 2, 20 * 15);
            var amplifier = AbilityPowerUtil.scale(event.abilityPower, 0, 3);

            var slowness = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, duration, amplifier);
            var slowFalling = new MobEffectInstance(MobEffects.SLOW_FALLING, duration, amplifier);
            
            livingEntity.addEffect(slowness);
            livingEntity.addEffect(slowFalling);
        }
    }
}
