package network.something.somemagic.magic.effect;

import net.minecraft.world.entity.Entity;
import network.something.somemagic.magic.effect.core.EntitySpellEffect;
import network.something.somemagic.magic.spell.core.Spell;

public class AccioSpellEffect extends EntitySpellEffect {

    public AccioSpellEffect(Spell spell, Entity target) {
        super(spell, target, 20 * 5);
    }

    @Override
    public void tick() {
        var motion = spell.caster.getEyePosition()
                .subtract(target.getEyePosition());

        target.setDeltaMovement(motion.normalize().scale(0.2));
    }
}
