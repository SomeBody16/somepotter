package network.something.somepotter.spell.basic_cast;

import ca.lukegrahamlandry.lib.config.ConfigWrapper;
import network.something.somepotter.SomePotter;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.util.ColorUtil;
import network.something.somepotter.util.ResourceUtil;
import network.something.somepotter.wand.GestureHandler;
import org.jetbrains.annotations.NotNull;

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
    public ColorUtil getColor() {
        return ColorUtil.DAMAGE;
    }

    @Override
    public @NotNull SpellListener<BasicCastSpell> getListener() {
        return new BasicCastListener();
    }

    @Override
    protected void registerGesture() {
        var alternatives = 3;

        for (var i = 1; i <= alternatives; i++) {
            var gesture = ResourceUtil.loadGesture(getId() + i);
            GestureHandler.registerGesture(gesture.name, gesture.points);
        }
    }
}
