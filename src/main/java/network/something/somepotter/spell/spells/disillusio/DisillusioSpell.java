package network.something.somepotter.spell.spells.disillusio;

import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class DisillusioSpell extends AbstractSpell {
    public static final String ID = "disillusio";

    public static final int DURATION = 20 * 60;

    public static final List<?> init = List.of(
            new DisillusioCastListener(),
            new DisillusioHitListener()
    );

    public DisillusioSpell() {
        super(ID);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.UTILITY;
    }
}
