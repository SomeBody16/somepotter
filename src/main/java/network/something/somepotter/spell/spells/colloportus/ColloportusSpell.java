package network.something.somepotter.spell.spells.colloportus;

import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class ColloportusSpell extends AbstractSpell {
    public static final String ID = "colloportus";

    public static final List<?> init = List.of(
            new ColloportusCastListener(),
            new ColloportusHitListener()
    );

    public ColloportusSpell() {
        super(ID);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.DAMAGE;
    }
}
