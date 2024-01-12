package network.something.somepotter.spell.basic_cast;

import network.something.somepotter.cast.Cast;
import network.something.somepotter.cast.projectile.ProjectileCast;
import network.something.somepotter.gesture.SpellGesture;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.util.ColorUtil;
import network.something.somepotter.util.ConfigUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
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
    public @NotNull List<SpellGesture> getGestures() {
        var result = new ArrayList<SpellGesture>();

        for (var spell : SpellInit.all()) {
            if (spell.getId().equals(ID)) {
                continue;
            }
            result.addAll(spell.getGestures());
        }

        return result;
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }

}
