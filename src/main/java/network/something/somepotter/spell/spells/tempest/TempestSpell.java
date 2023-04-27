package network.something.somepotter.spell.spells.tempest;

import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.spell.type.SpellType;
import network.something.somepotter.spell.type.SpellTypes;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class TempestSpell extends AbstractSpell {
    public static final String ID = "tempest";

    public static final List<?> init = List.of(
            new TempestCastListener(),
            new TempestHitListener()
    );

    public TempestSpell() {
        super(ID);
    }

    @Override
    public SpellType getType() {
        return SpellTypes.JINX;
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.DAMAGE;
    }

    @Override
    public int getCooldown() {
        return 20 * 15;
    }
}
