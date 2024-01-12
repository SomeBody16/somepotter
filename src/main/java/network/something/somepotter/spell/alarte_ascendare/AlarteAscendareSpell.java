package network.something.somepotter.spell.alarte_ascendare;

import network.something.somepotter.cast.Cast;
import network.something.somepotter.cast.projectile.ProjectileCast;
import network.something.somepotter.gesture.SpellGesture;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.util.ColorUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Shoots the target high into the air
 */
public class AlarteAscendareSpell extends Spell {
    public static final String ID = "alarte_ascendare";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<AlarteAscendareSpell> getListener() {
        return new AlarteAscendareListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileCast();
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.line(45, 10, 45, 75);
        gesture.draw.bezierCurve(45, 75, 45, 90, 50, 95, 60, 95);
        gesture.draw.bezierCurve(60, 95, 67, 95, 75, 85, 75, 80);
        gesture.draw.bezierCurve(75, 80, 75, 70, 67, 65, 50, 70);

        gesture.nextStroke();
        gesture.draw.line(35, 10, 35, 95);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }

    @Override
    public ColorUtil getColor() {
        return ColorUtil.FORCE;
    }
}
