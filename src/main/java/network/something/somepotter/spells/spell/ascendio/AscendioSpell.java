package network.something.somepotter.spells.spell.ascendio;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.mechanics.gesture.SpellGesture;
import network.something.somepotter.spells.cast.Cast;
import network.something.somepotter.spells.cast.self.SelfCast;
import network.something.somepotter.spells.requirement.Requirement;
import network.something.somepotter.spells.requirement.y_position.YPositionRequirement;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.spells.spell_type.charm.CharmType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Shoots the caster high into the air
 */
public class AscendioSpell extends Spell {

    public static final String ID = "ascendio";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<AscendioSpell> getListener() {
        return new AscendioListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new SelfCast();
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Items.ELYTRA);
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.line(35, 10, 35, 75);
        gesture.draw.bezierCurve(35, 75, 35, 90, 40, 95, 50, 95);
        gesture.draw.bezierCurve(50, 95, 57, 95, 65, 85, 65, 80);
        gesture.draw.bezierCurve(65, 80, 65, 70, 57, 65, 40, 70);

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
        result.add(YPositionRequirement.above(5000));
        return result;
    }
}
