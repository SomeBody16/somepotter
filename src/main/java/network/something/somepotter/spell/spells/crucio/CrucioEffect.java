package network.something.somepotter.spell.spells.crucio;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.spell.api.particle.SpellParticle;
import network.something.somepotter.spell.effect.SpellEffects;
import network.something.somepotter.util.SpellColor;

public class CrucioEffect extends MobEffect {

    public CrucioEffect() {
        super(MobEffectCategory.HARMFUL, SpellColor.UNFORGIVEABLE.getRGB24());
        addAttributeModifier(Attributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890",
                -0.15D, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level.isClientSide) {
            var damageSource = CrucioSpell.getDamageSource(entity);
            entity.hurt(damageSource, 0);
            entity.hurtMarked = true;
            SpellParticle.playTouchParticles(CrucioSpell.ID, (ServerLevel) entity.level, entity.getEyePosition());

            applyIfLooking(entity);
        }

        super.applyEffectTick(entity, amplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }


    public static void applyIfLooking(LivingEntity target) {
        var crucio = new MobEffectInstance(SpellEffects.CRUCIO.get(), CrucioSpell.REFRESH_DURATION, 10);
        var crucioApply = new MobEffectInstance(SpellEffects.CRUCIO_APPLY.get(), CrucioSpell.REFRESH_DURATION, 10);
        var areaOfEffect = target.getBoundingBox().inflate(CrucioSpell.RANGE);

        for (var entity : target.level.getEntities(target, areaOfEffect)) {
            if (entity instanceof LivingEntity possibleCaster) {
                if (possibleCaster.hasEffect(SpellEffects.CRUCIO_APPLY.get())
                        && isLookingAt(possibleCaster, target)) {
                    target.addEffect(crucio);
                }
            }
        }
    }

    private static boolean isLookingAt(LivingEntity caster, LivingEntity target) {
        if (caster == null) {
            return false;
        }

        Vec3 vec3 = caster.getViewVector(1.0F).normalize();
        Vec3 vec31 = new Vec3(target.getX() - caster.getX(), target.getEyeY() - caster.getEyeY(), target.getZ() - caster.getZ());
        double d0 = vec31.length();
        vec31 = vec31.normalize();
        double d1 = vec3.dot(vec31);
        return d1 > (1.0D - CrucioSpell.LOOK_THRESHOLD / d0) && caster.hasLineOfSight(target);
    }
}
