package network.something.somepotter.spell.spells.expelliarmus;

import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class ExpelliarmusSpell extends AbstractSpell {
    public static final String ID = "expelliarmus";

    public static final int ACCIO_DURATION = 20 * 5;
    public static final float PUSH_FORCE = 4.0f;

    public static final List<?> init = List.of(
            new ExpelliarmusCastListener(),
            new ExpelliarmusHitListener()
    );

    public ExpelliarmusSpell() {
        super(ID);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.DAMAGE;
    }

    @Override
    public int getCooldown() {
        return 20 * 10;
    }
}
