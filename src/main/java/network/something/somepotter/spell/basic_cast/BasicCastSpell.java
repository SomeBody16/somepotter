package network.something.somepotter.spell.basic_cast;

import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;

public class BasicCastSpell extends Spell {

    public static final String ID = "basic_cast";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public int getAbilityPower() {
        return 6;
    }

    @Override
    public SpellListener<BasicCastSpell> getListener() {
        return new BasicCastListener();
    }

    @Override
    protected void registerGesture() {

    }
}
