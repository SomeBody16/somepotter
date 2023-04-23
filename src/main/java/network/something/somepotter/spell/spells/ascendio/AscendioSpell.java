package network.something.somepotter.spell.spells.ascendio;

import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class AscendioSpell extends AbstractSpell {
    public static final String ID = "ascendio";

    public static final float FORCE = 2.0f;

    public static final List<?> init = List.of(
            new AscendioCastListener(),
            new AscendioHitListener()
    );

    public AscendioSpell() {
        super(ID);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.FORCE;
    }
}
