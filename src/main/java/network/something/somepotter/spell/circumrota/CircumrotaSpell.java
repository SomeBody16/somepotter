package network.something.somepotter.spell.circumrota;

import network.something.somepotter.cast.Cast;
import network.something.somepotter.cast.projectile.ProjectileCast;
import network.something.somepotter.gesture.SpellGesture;
import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.spell_type.SpellType;
import network.something.somepotter.spell_type.charm.CharmType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Rotates the target
 * <p>
 * Will turn entity 180 degrees
 * Will not work on blocks
 */
public class CircumrotaSpell extends Spell {
    public static final String ID = "circumrota";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    @NotNull
    public SpellListener<CircumrotaSpell> getListener() {
        return new CircumrotaListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileCast();
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.line(35, 90, 35, 20);
        gesture.draw.bezierCurve(35, 20, 35, 10, 42, 10, 50, 10);
        gesture.draw.bezierCurve(50, 10, 62, 10, 65, 20, 45, 30);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }

    @Override
    public @NotNull SpellType getType() {
        return SpellTypeInit.get(CharmType.ID);
    }
}
