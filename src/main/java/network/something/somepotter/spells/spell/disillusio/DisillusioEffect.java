package network.something.somepotter.spells.spell.disillusio;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import network.something.somepotter.spells.spell.SpellEffect;

public class DisillusioEffect extends SpellEffect<DisillusioSpell> {

    public DisillusioEffect() {
        super(MobEffectCategory.BENEFICIAL, DisillusioSpell.ID);
    }

    @Override
    public void tick(LivingEntity target, int amplifier) {
        target.setInvisible(true);
    }

    @Override
    public void onExpired(LivingEntity target, int amplifier) {
        super.onExpired(target, amplifier);
        target.setInvisible(false);
    }
}
