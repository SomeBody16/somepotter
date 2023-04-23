package network.something.somepotter.spell.spells.revelio;

import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class RevelioSpell extends AbstractSpell {
    public static final String ID = "revelio";

    public static final List<?> init = List.of(
            new RevelioCastListener()
    );
    public static final float RANGE = 16;
    public static final int DURATION = 20 * 10;

    public RevelioSpell() {
        super(ID);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.UTILITY;
    }
}
