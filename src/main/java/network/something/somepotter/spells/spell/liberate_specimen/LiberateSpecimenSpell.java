package network.something.somepotter.spells.spell.liberate_specimen;

import network.something.somepotter.mechanics.gesture.SpellGesture;
import network.something.somepotter.spells.cast.Cast;
import network.something.somepotter.spells.cast.projectile.ProjectileCast;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.spells.spell_type.charm.CharmType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiberateSpecimenSpell extends Spell {
    public static final String ID = "liberate_specimen";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<LiberateSpecimenSpell> getListener() {
        return new LiberateSpecimenListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileCast()
                .setRange(10);
    }

    @Override
    public @NotNull SpellType getType() {
        return new CharmType();
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.circle(50, 50, 40);
        gesture.nextStroke();

        gesture.draw.line(50, 50, 50, 95);
        gesture.nextStroke();

        gesture.draw.line(40, 95, 60, 95);
        gesture.nextStroke();

        gesture.draw.bezierCurve(50, 50, 20, 10, 80, 10, 50, 50);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }
}
