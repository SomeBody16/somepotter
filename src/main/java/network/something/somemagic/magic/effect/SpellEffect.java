package network.something.somemagic.magic.effect;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public abstract class SpellEffect {

    protected LivingEntity caster;
    protected Entity target;
    protected int duration;

    public SpellEffect(LivingEntity caster, Entity target, int duration) {
        this.caster = caster;
        this.target = target;
        this.duration = duration;
    }

    public abstract void tick();

    public boolean isExpired() {
        return this.duration-- <= 0;
    }

}
