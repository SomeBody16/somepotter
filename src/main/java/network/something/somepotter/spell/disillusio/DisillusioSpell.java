package network.something.somepotter.spell.disillusio;

import network.something.somepotter.cast.Cast;
import network.something.somepotter.cast.projectile_or_self.ProjectileOrSelfCast;
import network.something.somepotter.gesture.SpellGesture;
import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.spell_type.SpellType;
import network.something.somepotter.spell_type.charm.CharmType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Makes the target invisible
 */
public class DisillusioSpell extends Spell {

    public static final String ID = "disillusio";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<DisillusioSpell> getListener() {
        return new DisillusioListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileOrSelfCast();
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.bezierCurve(10, 35, 30, 20, 60, 20, 90, 50);
        gesture.draw.bezierCurve(90, 50, 60, 80, 30, 70, 15, 50);
        gesture.draw.bezierCurve(15, 50, 30, 35, 60, 35, 70, 50);
        gesture.draw.bezierCurve(70, 50, 70, 60, 35, 60, 35, 50);

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
