package network.something.somepotter.spell.spells.levioso;

import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class LeviosoSpell extends AbstractSpell {
    public static final String ID = "levioso";

    public static final int REFRESH_DURATION = 20 * 3;
    public static final float PULL_STRENGTH = 0.25f;

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

    @Override
    public int getCooldown() {
        return 20 * 5;
    }
}
