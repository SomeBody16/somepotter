package network.something.somepotter.spell.spells.crucio;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import network.something.somepotter.util.SpellColor;

public class CrucioApplyEffect extends MobEffect {
    public CrucioApplyEffect() {
        super(MobEffectCategory.HARMFUL, SpellColor.UNFORGIVEABLE.getRGB24());
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        super.applyEffectTick(entity, amplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
