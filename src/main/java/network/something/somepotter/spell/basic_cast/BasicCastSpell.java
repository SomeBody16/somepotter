package network.something.somepotter.spell.basic_cast;

import ca.lukegrahamlandry.lib.config.ConfigWrapper;
import network.something.somepotter.SomePotter;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;

import java.util.function.Supplier;

public class BasicCastSpell extends Spell {

    public static final String ID = "basic_cast";
    public static final Supplier<BasicCastConfig> CONFIG = ConfigWrapper.server(BasicCastConfig.class)
            .dir(SomePotter.MOD_ID)
            .named(ID);

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public SpellListener<BasicCastSpell> getListener() {
        return new BasicCastListener();
    }

    @Override
    protected void registerGesture() {

    }
}
