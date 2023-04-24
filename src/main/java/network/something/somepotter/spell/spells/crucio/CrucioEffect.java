package network.something.somepotter.spell.spells.crucio;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import network.something.somepotter.spell.api.particle.SpellParticle;
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
            entity.hurt(damageSource, 1);
            entity.hurtMarked = true;
            SpellParticle.playTouchParticles(CrucioSpell.ID, (ServerLevel) entity.level, entity.getEyePosition());
        }

        super.applyEffectTick(entity, amplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

}
