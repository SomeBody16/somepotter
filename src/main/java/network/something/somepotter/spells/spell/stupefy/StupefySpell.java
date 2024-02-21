package network.something.somepotter.spells.spell.stupefy;

import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.mechanics.gesture.SpellGesture;
import network.something.somepotter.spells.cast.Cast;
import network.something.somepotter.spells.cast.projectile.ProjectileCast;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.spells.spell_type.charm.CharmType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Stuns the target
 * <p>
 * Will apply blindness for short amount of time
 * Will apply nausea for short amount of time
 * Will throw the target back and random it looks direction
 */
public class StupefySpell extends Spell {

    public static final String ID = "stupefy";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<StupefySpell> getListener() {
        return new StupefyListener();
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

        gesture.draw.line(65, 20, 20, 70);
        gesture.draw.line(20, 70, 80, 75);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }
}
