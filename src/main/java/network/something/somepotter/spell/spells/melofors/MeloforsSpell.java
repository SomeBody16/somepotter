package network.something.somepotter.spell.spells.melofors;

import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class MeloforsSpell extends AbstractSpell {
    public static final String ID = "melofors";

    public static final List<?> init = List.of(
            new MeloforsCastListener(),
            new MeloforsHitListener()
    );

    public MeloforsSpell() {
        super(ID);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.UTILITY;
    }
}
