package network.something.somepotter.spell.spells.herbivicus;

import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class HerbivicusSpell extends AbstractSpell {
    public static final String ID = "herbivicus";

    public static final List<?> init = List.of(
            new HerbivicusCastListener(),
            new HerbivicusHitListener()
    );

    public HerbivicusSpell() {
        super(ID);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.UTILITY;
    }
}
