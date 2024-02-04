package network.something.somepotter.spells.spell.incarcerous_captura;

import network.something.somepotter.mechanics.gesture.SpellGesture;
import network.something.somepotter.spells.cast.Cast;
import network.something.somepotter.spells.cast.projectile.ProjectileCast;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.spells.spell_type.charm.CharmType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IncarcerousCapturaSpell extends Spell {

    public static final String ID = "incarcerous_captura";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<IncarcerousCapturaSpell> getListener() {
        return new IncarcerousCapturaListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileCast()
                .setRange(5);
    }

    @Override
    public @NotNull SpellType getType() {
        return new CharmType();
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.circle(25, 25, 10);
        gesture.nextStroke();

        gesture.draw.line(25, 35, 25, 65);
        gesture.nextStroke();

        gesture.draw.circle(25, 75, 10);
        gesture.nextStroke();

        gesture.draw.bezierCurve(35, 75, 50, 100, 50, 0, 75, 25);
        gesture.nextStroke();

        gesture.draw.circle(85, 25, 10);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        var gesture = new SpellGesture(ID);

        gesture.draw.circle(25, 25, 10);
        gesture.nextStroke();

        gesture.draw.line(25, 35, 25, 65);
        gesture.nextStroke();

        gesture.draw.circle(25, 75, 10);
        gesture.nextStroke();

        gesture.draw.line(35, 75, 75, 25);
        gesture.nextStroke();

        gesture.draw.circle(85, 25, 10);

        return List.of(gesture);
    }
}
