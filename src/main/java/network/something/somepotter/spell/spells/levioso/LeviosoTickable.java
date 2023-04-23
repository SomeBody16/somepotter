package network.something.somepotter.spell.spells.levioso;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import network.something.somepotter.spell.tickable.core.EntitySpellTickable;

public class LeviosoTickable extends EntitySpellTickable {
    public LeviosoTickable(LivingEntity caster, int duration, Entity target) {
        super(caster, duration, target);
    }

    @Override
    public void tick() {

    }
}
