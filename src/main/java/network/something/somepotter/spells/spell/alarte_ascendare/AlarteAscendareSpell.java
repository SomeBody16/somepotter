package network.something.somepotter.spells.spell.alarte_ascendare;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.mechanics.gesture.SpellGesture;
import network.something.somepotter.spells.cast.Cast;
import network.something.somepotter.spells.cast.projectile.ProjectileCast;
import network.something.somepotter.spells.requirement.Requirement;
import network.something.somepotter.spells.requirement.spell_learned.SpellLearnedRequirement;
import network.something.somepotter.spells.requirement.y_position.YPositionRequirement;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell.ascendio.AscendioSpell;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.spells.spell_type.charm.CharmType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Shoots the target high into the air
 */
public class AlarteAscendareSpell extends Spell {
    public static final String ID = "alarte_ascendare";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<AlarteAscendareSpell> getListener() {
        return new AlarteAscendareListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileCast();
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Items.FIREWORK_ROCKET);
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.line(45, 10, 45, 75);
        gesture.draw.bezierCurve(45, 75, 45, 90, 50, 95, 60, 95);
        gesture.draw.bezierCurve(60, 95, 67, 95, 75, 85, 75, 80);
        gesture.draw.bezierCurve(75, 80, 75, 70, 67, 65, 50, 70);

        gesture.nextStroke();
        gesture.draw.line(35, 10, 35, 95);

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

    @Override
    public List<Requirement> getRequirements() {
        var result = super.getRequirements();
        result.add(SpellLearnedRequirement.of(AscendioSpell.ID));
        result.add(YPositionRequirement.above(5000));
        return result;
    }
}
