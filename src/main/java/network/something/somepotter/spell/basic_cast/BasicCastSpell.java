package network.something.somepotter.spell.basic_cast;

import network.something.somepotter.cast.Cast;
import network.something.somepotter.cast.projectile.ProjectileCast;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.util.ColorUtil;
import network.something.somepotter.util.ConfigUtil;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BasicCastSpell extends Spell {

    public static final String ID = "basic_cast";
    public static final Supplier<BasicCastConfig> CONFIG = ConfigUtil.server(BasicCastConfig.class, ID);

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
    public @NotNull Cast getCast() {
        return new ProjectileCast();
    }

    @Override
    protected void registerGesture() {
//        var alternatives = 3;
//
//        for (var i = 1; i <= alternatives; i++) {
//            var gesture = ResourceUtil.loadGesture(getId() + i);
//            GestureHandler.registerGesture(gesture.name, gesture.points);
//        }
    }
}
