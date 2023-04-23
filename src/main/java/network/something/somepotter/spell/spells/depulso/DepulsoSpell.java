package network.something.somepotter.spell.spells.depulso;

import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class DepulsoSpell extends AbstractSpell {
    public static final String ID = "depulso";

    public static final float STRENGTH = 3.0F;

    public static final List<?> init = List.of(
            new DepulsoCastListener(),
            new DepulsoHitListener()
    );

    public DepulsoSpell() {
        super(ID);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.FORCE;
    }
}
