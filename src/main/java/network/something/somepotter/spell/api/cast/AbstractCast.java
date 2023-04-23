package network.something.somepotter.spell.api.cast;

import net.minecraft.world.entity.LivingEntity;

public abstract class AbstractCast {

    public abstract void execute(LivingEntity caster, String spellId);

}
