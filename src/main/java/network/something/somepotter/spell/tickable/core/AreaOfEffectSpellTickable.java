package network.something.somepotter.spell.tickable.core;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

public abstract class AreaOfEffectSpellTickable extends SpellTickable {
    public final AABB areaOfEffect;

    public AreaOfEffectSpellTickable(LivingEntity caster, AABB areaOfEffect, int duration) {
        super(caster, duration);
        this.areaOfEffect = areaOfEffect;
    }
}
