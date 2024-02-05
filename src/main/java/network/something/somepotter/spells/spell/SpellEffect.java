package network.something.somepotter.spells.spell;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.particle.ParticleEffects;
import org.jetbrains.annotations.NotNull;

public abstract class SpellEffect<T extends Spell> extends MobEffect {

    protected final String spellId;

    protected SpellEffect(MobEffectCategory pCategory, String spellId) {
        super(pCategory, SpellInit.get(spellId).getColor().getRGB());
        this.spellId = spellId;
    }

    @SuppressWarnings("unchecked")
    public T getSpell() {
        return (T) SpellInit.get(spellId);
    }

    public void tick(LivingEntity target, int amplifier) {
    }

    public void onAdded(LivingEntity target, int amplifier, LivingEntity caster) {
        ParticleEffects.touch(target.level, target.getEyePosition(), getSpell().getColor());
    }

    public void onExpired(LivingEntity target, int amplifier) {
        ParticleEffects.touch(target.level, target.getEyePosition(), getSpell().getColor());
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity pLivingEntity, int pAmplifier) {
        super.applyEffectTick(pLivingEntity, pAmplifier);
        tick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
