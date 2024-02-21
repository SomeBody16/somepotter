package network.something.somepotter.spells.spell.expelliarmus;

import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.mechanics.gesture.SpellGesture;
import network.something.somepotter.spells.cast.Cast;
import network.something.somepotter.spells.cast.projectile.ProjectileCast;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.spells.spell_type.charm.CharmType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Disarms the target
 * <p>
 * Will remove the item in the target's hand, prioritizing wands
 * If nothing in hand apply knock back
 */
public class ExpelliarmusSpell extends Spell {
    public static final String ID = "expelliarmus";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<ExpelliarmusSpell> getListener() {
        return new ExpelliarmusListener();
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
    public int getSkillPointCost() {
        return 4;
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.bezierCurve(5, 10, 6, 5, 9, 5, 10, 10);
        gesture.draw.line(10, 10, 35, 65);

        gesture.draw.bezierCurve(35, 65, 45, 75, 70, 65, 65, 50);
        gesture.draw.bezierCurve(65, 50, 55, 30, 25, 45, 40, 55);
        gesture.draw.bezierCurve(40, 55, 40, 60, 55, 60, 50, 50);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return new ArrayList<>();
    }
}
