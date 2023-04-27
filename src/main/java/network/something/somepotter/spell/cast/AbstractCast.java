package network.something.somepotter.spell.cast;

import net.minecraft.world.entity.LivingEntity;

public abstract class AbstractCast {

    public abstract String getId();

    public abstract void execute(LivingEntity caster, String spellId);

}
