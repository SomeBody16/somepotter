package network.something.somepotter.spell.spells.stupefy;

import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class StupefySpell extends AbstractSpell {
    public static final String ID = "stupefy";

    public static final float STRENGTH = 2.0F;

    public static final List<?> init = List.of(
            new StupefyCastListener(),
            new StupefyHitListener()
    );

    public StupefySpell() {
        super(ID);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.UTILITY;
    }
}
