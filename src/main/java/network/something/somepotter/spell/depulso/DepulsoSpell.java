package network.something.somepotter.spell.depulso;

import network.something.somepotter.cast.Cast;
import network.something.somepotter.cast.projectile.ProjectileCast;
import network.something.somepotter.gesture.SpellGesture;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Pushes the target away
 * <p>
 * Will push the target away from the caster
 */
public class DepulsoSpell extends Spell {
    public static final String ID = "depulso";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<DepulsoSpell> getListener() {
        return new DepulsoListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileCast();
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.bezierCurve(5, 5, 25, 90, 75, 90, 95, 5);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }
}
