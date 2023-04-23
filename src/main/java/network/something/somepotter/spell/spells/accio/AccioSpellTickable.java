package network.something.somepotter.spell.spells.accio;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import network.something.somepotter.spell.tickable.core.EntitySpellTickable;

public class AccioSpellTickable extends EntitySpellTickable {

    public AccioSpellTickable(LivingEntity caster, int duration, Entity target) {
        super(caster, duration, target);
    }

    @Override
    public void tick() {
        var motion = caster.getEyePosition()
                .subtract(target.getEyePosition());

        target.setDeltaMovement(motion.normalize().scale(AccioSpell.PULL_STRENGTH));
        target.hurtMarked = true;
    }

}
