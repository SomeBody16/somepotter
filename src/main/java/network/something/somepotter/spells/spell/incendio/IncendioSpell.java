package network.something.somepotter.spells.spell.incendio;

import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.mechanics.gesture.SpellGesture;
import network.something.somepotter.spells.cast.Cast;
import network.something.somepotter.spells.cast.projectile.ProjectileCast;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.spells.spell_type.charm.CharmType;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * Sets the target on fire
 * <p>
 * Will set the target on fire for a short period of time
 */
public class IncendioSpell extends Spell {
    public static final String ID = "incendio";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<IncendioSpell> getListener() {
        return new IncendioListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileCast();
    }

    @Override
    public @NotNull SpellType getType() {
        return SpellTypeInit.get(CharmType.ID);
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.bezierCurve(25, 80, 15, 55, 50, 40, 50, 10);
        gesture.draw.bezierCurve(50, 10, 45, 30, 80, 65, 65, 85);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }

    @Override
    public Color getColor() {
        var random = new Random().nextInt(100);
        if (random < 33) {
            return Color.RED;
        } else if (random < 66) {
            return Color.ORANGE;
        } else {
            return Color.YELLOW;
        }
    }
}
