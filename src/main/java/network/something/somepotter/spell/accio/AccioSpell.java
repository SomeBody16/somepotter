package network.something.somepotter.spell.accio;

import network.something.somepotter.cast.Cast;
import network.something.somepotter.cast.touch.TouchCast;
import network.something.somepotter.gesture.SpellGesture;
import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.spell_type.SpellType;
import network.something.somepotter.spell_type.charm.CharmType;
import network.something.somepotter.util.ConfigUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

/**
 * Summons an object towards the caster
 * <p>
 * Works like a magnet for items only
 */
public class AccioSpell extends Spell {

    public static final String ID = "accio";
    public static final Supplier<AccioConfig> CONFIG = ConfigUtil.server(AccioConfig.class, ID);

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public float getAreaOfEffect() {
        return 1.0F;
    }

    @Override
    public @NotNull Cast getCast() {
        return new TouchCast();
    }

    @Override
    public @NotNull SpellType getType() {
        return SpellTypeInit.get(CharmType.ID);
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.bezierCurve(5, 95, 25, 10, 75, 10, 95, 95);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }

    @Override
    public @NotNull SpellListener<AccioSpell> getListener() {
        return new AccioListener();
    }
}
