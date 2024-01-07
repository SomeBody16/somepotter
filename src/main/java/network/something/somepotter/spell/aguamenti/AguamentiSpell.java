package network.something.somepotter.spell.aguamenti;

import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;

public class AguamentiSpell extends Spell {

    public static final String ID = "aguamenti";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public SpellListener<AguamentiSpell> getListener() {
        return new AguamentiListener();
    }
}
