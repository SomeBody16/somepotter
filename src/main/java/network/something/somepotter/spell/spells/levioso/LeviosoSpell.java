package network.something.somepotter.spell.spells.levioso;

import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class LeviosoSpell extends AbstractSpell {
    public static final String ID = "levioso";

    public static final int DURATION = 20 * 10;

    public static final List<?> init = List.of(
            new LeviosoCastListener(),
            new LeviosoHitListener()
    );

    public LeviosoSpell() {
        super(ID);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.DAMAGE;
    }
}
