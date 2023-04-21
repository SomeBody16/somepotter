package network.something.somemagic.magic.spell.core;

import net.minecraft.world.entity.LivingEntity;
import network.something.somemagic.SomeMagic;
import network.something.somemagic.util.SpellColor;

public class UnknownSpell extends Spell {
    public static final String ID = "unknown";

    public UnknownSpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public int getCooldownTicks() {
        return 0;
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.ESSENTIAL;
    }

    @Override
    public void cast() {
        SomeMagic.LOGGER.warn("{} tried to cast unknown spell", caster.getDisplayName());
    }
}

