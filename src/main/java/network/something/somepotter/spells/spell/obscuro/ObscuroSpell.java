package network.something.somepotter.spells.spell.obscuro;

import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.mechanics.gesture.SpellGesture;
import network.something.somepotter.spells.cast.Cast;
import network.something.somepotter.spells.cast.projectile.ProjectileCast;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.spells.spell_type.jinx.JinxType;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * Causes the target to be unable to see
 * <p>
 * Applies blindness to the target
 */
public class ObscuroSpell extends Spell {

    public static final String ID = "obscuro";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<ObscuroSpell> getListener() {
        return new ObscuroListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileCast();
    }

    @Override
    public @NotNull SpellType getType() {
        return SpellTypeInit.get(JinxType.ID);
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.line(20, 90, 55, 20);
        gesture.draw.bezierCurve(55, 20, 95, 30, 95, 85, 35, 75);
        gesture.draw.bezierCurve(35, 75, 0, 70, -10, 10, 45, 10);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }

    @Override
    public Color getColor() {
        var random = new Random().nextInt(100);
        if (random < 50) {
            return Color.BLACK;
        } else {
            return Color.GRAY;
        }
    }
}
