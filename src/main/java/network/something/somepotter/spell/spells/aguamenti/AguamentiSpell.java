package network.something.somepotter.spell.spells.aguamenti;

import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class AguamentiSpell extends AbstractSpell {
    public static final String ID = "aguamenti";

    public static final List<?> init = List.of(
            new AguamentiCastListener(),
            new AguamentiHitListener()
    );

    public AguamentiSpell() {
        super(ID);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.UTILITY;
    }
}
