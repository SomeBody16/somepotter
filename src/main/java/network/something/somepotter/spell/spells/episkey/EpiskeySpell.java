package network.something.somepotter.spell.spells.episkey;

import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.spell.type.SpellType;
import network.something.somepotter.spell.type.SpellTypes;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class EpiskeySpell extends AbstractSpell {
    public static final String ID = "episkey";

    public static final float HEAL_IF_ABOVE = 0.7f;
    public static final int HEAL_DURATION = 20 * 6;
    public static final int HEAL_AMPLIFIER = 1;

    public static final List<?> init = List.of(
            new EpiskeyCastListener(),
            new EpiskeyHitListener()
    );

    public EpiskeySpell() {
        super(ID);
    }

    @Override
    public SpellType getType() {
        return SpellTypes.HEALING;
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.ESSENTIAL;
    }

    @Override
    public int getCooldown() {
        return 20 * 10;
    }
}
