package network.something.somepotter.spell.spells.alohomora;

import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class AlohomoraSpell extends AbstractSpell {
    public static final String ID = "alohomora";

    public static final List<?> init = List.of(
            new AlohomoraCastListener(),
            new AlohomoraHitListener()
    );

    public AlohomoraSpell() {
        super(ID);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.ESSENTIAL;
    }
}
