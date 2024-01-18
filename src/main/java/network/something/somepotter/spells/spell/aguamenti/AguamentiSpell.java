package network.something.somepotter.spells.spell.aguamenti;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.mechanics.gesture.SpellGesture;
import network.something.somepotter.spells.cast.Cast;
import network.something.somepotter.spells.cast.projectile_or_self.ProjectileOrSelfCast;
import network.something.somepotter.spells.requirement.Requirement;
import network.something.somepotter.spells.requirement.is_underwater.IsUnderwaterRequirement;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.spells.spell_type.conjuration.ConjurationType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Creates a jet of water from the caster's wand
 * <p>
 * If cast at block, it will extinguish fire if possible, otherwise will create water source block
 * If cast at entity, it will extinguish fire if possible
 */
public class AguamentiSpell extends Spell {

    public static final String ID = "aguamenti";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public float getAreaOfEffect() {
        return 2F;
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileOrSelfCast();
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Items.WATER_BUCKET);
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.bezierCurve(5, 55, 10, 80, 30, 80, 45, 50);
        gesture.draw.bezierCurve(45, 50, 70, 20, 80, 10, 95, 50);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }

    @Override
    public @NotNull SpellType getType() {
        return SpellTypeInit.get(ConjurationType.ID);
    }

    @Override
    public @NotNull SpellListener<AguamentiSpell> getListener() {
        return new AguamentiListener();
    }

    @Override
    public List<Requirement> getRequirements() {
        var result = super.getRequirements();
        result.add(IsUnderwaterRequirement.of());
        return result;
    }
}
