package network.something.somepotter.spell.spells.incendio;

import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class IncendioSpell extends AbstractSpell {
    public static final String ID = "incendio";

    public static final int FIRE_DURATION = 20 * 10;

    public static final List<?> init = List.of(
            new IncendioCastListener(),
            new IncendioHitListener()
    );

    public IncendioSpell() {
        super(ID);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.FORCE;
    }
}
