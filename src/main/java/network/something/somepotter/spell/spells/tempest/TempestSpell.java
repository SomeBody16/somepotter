package network.something.somepotter.spell.spells.tempest;

import network.something.somepotter.spell.spells.AbstractSpell;
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
    public SpellColor getColor() {
        return SpellColor.DAMAGE;
    }
}
