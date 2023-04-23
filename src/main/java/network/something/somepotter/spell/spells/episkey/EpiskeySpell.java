package network.something.somepotter.spell.spells.episkey;

import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class EpiskeySpell extends AbstractSpell {
    public static final String ID = "episkey";

    public static final float HEAL_IF_ABOVE = 0.7f;
    public static final int HEAL_DURATION = 20 * 5;
    public static final int HEAL_AMPLIFIER = 1;

    public static final List<?> init = List.of(
            new EpiskeyCastListener(),
            new EpiskeyHitListener()
    );

    public EpiskeySpell() {
        super(ID);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.ESSENTIAL;
    }
}
