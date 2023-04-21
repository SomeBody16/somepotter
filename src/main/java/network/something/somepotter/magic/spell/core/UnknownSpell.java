package network.something.somepotter.magic.spell.core;

import net.minecraft.world.entity.LivingEntity;
import network.something.somepotter.SomePotter;
import network.something.somepotter.util.SpellColor;

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
        SomePotter.LOGGER.warn("{} tried to cast unknown spell", caster.getDisplayName());
    }
}

