package network.something.somepotter.spell.spells.reparo;

import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class ReparoSpell extends AbstractSpell {
    public static final String ID = "reparo";

    public static final int RANGE = 1;
    public static final int ENTRIES_PER_PLAYER = 1024;
    public static final int REPAIR_EACH_N_TICK = 5;

    public static final List<?> init = List.of(
            new ReparoCastListener(),
            new ReparoHitListener()
    );

    public ReparoSpell() {
        super(ID);
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
