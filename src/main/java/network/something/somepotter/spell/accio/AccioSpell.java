package network.something.somepotter.spell.accio;

import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;

public class AccioSpell extends Spell {

    public static final String ID = "accio";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public int getAbilityPower() {
        return 12;
    }

    @Override
    public SpellListener<AccioSpell> getListener() {
        return new AccioListener();
    }
}
