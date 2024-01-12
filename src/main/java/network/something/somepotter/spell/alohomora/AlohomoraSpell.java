package network.something.somepotter.spell.alohomora;

import network.something.somepotter.cast.Cast;
import network.something.somepotter.cast.touch.TouchCast;
import network.something.somepotter.gesture.SpellGesture;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.util.ColorUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Opens locked doors
 * <p>
 * Open doors, trapdoors, fence gates, and iron doors
 */
public class AlohomoraSpell extends Spell {

    public static final String ID = "alohomora";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<AlohomoraSpell> getListener() {
        return new AlohomoraListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new TouchCast()
                .setRange(3);
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.bezierCurve(50, 10, 70, 10, 90, 30, 90, 50);
        gesture.removeFromStart(15);

        gesture.draw.bezierCurve(90, 50, 90, 70, 70, 90, 50, 90);
        gesture.draw.bezierCurve(50, 90, 30, 90, 10, 70, 10, 50);
        gesture.draw.bezierCurve(10, 50, 10, 30, 30, 10, 50, 10);
        gesture.draw.line(50, 10, 50, 98);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }

    @Override
    public ColorUtil getColor() {
        return ColorUtil.ESSENTIAL;
    }
}
