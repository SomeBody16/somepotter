package network.something.somepotter.spells.spell.tempest;

import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.mechanics.gesture.SpellGesture;
import network.something.somepotter.spells.cast.Cast;
import network.something.somepotter.spells.cast.projectile.ProjectileCast;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.spells.spell_type.jinx.JinxType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Creates a storm
 * <p>
 * Creates a storm at the target location (area) and strikes lightning (continuously) at the target
 * Requires a clear view of the sky
 */
public class TempestSpell extends Spell {

    public static final String ID = "tempest";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<TempestSpell> getListener() {
        return new TempestListener();
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
        
        gesture.draw.line(50, 100, 50, 50); // Draw a vertical line downwards
        gesture.draw.circle(50, 45, 10); // Small circle to represent focus of energy
        gesture.draw.bezierCurve(50, 35, 45, 30, 55, 20, 50, 10); // Zigzagging down
        gesture.draw.bezierCurve(50, 10, 60, 0, 40, -10, 50, -20); // Branching out
        gesture.draw.line(50, -20, 50, -30); // Straight line to represent the final strike

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }
}
