package network.something.somemagic.magic.spell;

import net.minecraft.world.entity.LivingEntity;
import network.something.somemagic.SomeMagic;

public class UnknownSpell extends Spell {
    public static final String ID = "unknown";

    public UnknownSpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public void cast() {
        SomeMagic.LOGGER.warn("{} tried to cast unknown spell", caster.getDisplayName());
    }
}

