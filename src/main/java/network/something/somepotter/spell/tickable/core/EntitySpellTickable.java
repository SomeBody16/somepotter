package network.something.somepotter.spell.tickable.core;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public abstract class EntitySpellTickable extends SpellTickable {
    public final Entity target;

    public EntitySpellTickable(LivingEntity caster, int duration, Entity target) {
        super(caster, duration);
        this.target = target;
    }
}
