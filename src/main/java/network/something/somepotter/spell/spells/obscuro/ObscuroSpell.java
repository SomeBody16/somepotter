package network.something.somepotter.spell.spells.obscuro;

import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class ObscuroSpell extends AbstractSpell {
    public static final String ID = "obscuro";

    public static final int DURATION = 20 * 10;

    public static final List<?> init = List.of(
            new ObscuroCastListener(),
            new ObscuroHitListener()
    );

    public ObscuroSpell() {
        super(ID);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.ESSENTIAL;
    }
}
