package network.something.somepotter.spell.spells;

import network.something.somepotter.util.SpellColor;

public class UnknownSpell extends AbstractSpell {

    public static final UnknownSpell instance = new UnknownSpell();

    public UnknownSpell() {
        super("unknown");
    }

    @Override
    public SpellColor getColor() {
        return null;
    }
}
