package network.something.somepotter.spells.spell.incarcerous_captura;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import network.something.somepotter.particle.ParticleEffects;
import network.something.somepotter.spells.tickable.Tickable;

public class HoldingProcessTickable extends Tickable {

    protected IncarcerousCapturaSpell spell;
    protected ServerPlayer caster;
    protected LivingEntity target;

    public HoldingProcessTickable(int duration, ServerPlayer caster, LivingEntity target) {
        super(duration);
        this.caster = caster;
        this.target = target;
    }

    @Override
    public void onExpired() {
        ParticleEffects.touch(caster.level, caster.getEyePosition(), spell.getColor());
        caster.level.addFreshEntity(target);
    }
}
