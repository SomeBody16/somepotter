package network.something.somepotter.spell.spells.accio;

import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class AccioSpell extends AbstractSpell {
    public static final String ID = "accio";

    public static final int PULL_DURATION = 20 * 5;
    public static final float BLOCK_HIT_RANGE = 3;

    public static final List<?> init = List.of(
            new AccioCastListener(),
            new AccioHitListener()
    );

    public AccioSpell() {
        super(ID);
        new AccioCastListener();
        new AccioHitListener();
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.UTILITY;
    }
}
