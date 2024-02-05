package network.something.somepotter.spells.spell.reducio;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import network.something.somepotter.init.EffectInit;
import network.something.somepotter.spells.spell.SpellEffect;
import network.something.somepotter.spells.spell.engorgio.EngorgioSpell;
import virtuoel.pehkui.api.ScaleTypes;

public class ReducioEffect extends SpellEffect<EngorgioSpell> {

    public ReducioEffect() {
        super(MobEffectCategory.NEUTRAL, EngorgioSpell.ID);
    }

    @Override
    public void onAdded(LivingEntity target, int amplifier, LivingEntity caster) {
        super.onAdded(target, amplifier, caster);

        var scaleData = ScaleTypes.BASE.getScaleData(target);
        var step = scaleData.getScale() > 1 ? 0.5F : 0.25F;
        var newScale = scaleData.getScale() - step;
        newScale = Math.max(newScale, 0.025F);

        scaleData.setTargetScale(newScale);

        if (newScale >= 1) {
            target.removeEffect(EffectInit.REDUCIO.get());
        }
        if (newScale <= 1) {
            target.removeEffect(EffectInit.ENGORGIO.get());
        }
    }

    @Override
    public void onExpired(LivingEntity target, int amplifier) {
        super.onExpired(target, amplifier);

        var scaleData = ScaleTypes.BASE.getScaleData(target);
        scaleData.setTargetScale(1);
    }
}
