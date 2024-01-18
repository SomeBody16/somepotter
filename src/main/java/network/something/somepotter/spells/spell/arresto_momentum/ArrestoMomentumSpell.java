package network.something.somepotter.spells.spell.arresto_momentum;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.mechanics.gesture.SpellGesture;
import network.something.somepotter.spells.cast.Cast;
import network.something.somepotter.spells.cast.projectile_or_self.ProjectileOrSelfCast;
import network.something.somepotter.spells.requirement.Requirement;
import network.something.somepotter.spells.requirement.advancement.AdvancementRequirement;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.spells.spell_type.charm.CharmType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Slows the target's momentum
 * <p>
 * Applies a slowness and slow falling effect to the target
 */
public class ArrestoMomentumSpell extends Spell {

    public static final String ID = "arresto_momentum";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<ArrestoMomentumSpell> getListener() {
        return new ArrestoMomentumListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileOrSelfCast();
    }

    @Override
    public @NotNull SpellType getType() {
        return SpellTypeInit.get(CharmType.ID);
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Items.FEATHER);
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.line(10, 90, 30, 10);
        gesture.draw.line(30, 10, 50, 90);
        gesture.draw.line(50, 90, 70, 10);
        gesture.draw.line(70, 10, 90, 90);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }

    @Override
    public List<Requirement> getRequirements() {
        var result = super.getRequirements();
        result.add(AdvancementRequirement.of(new ResourceLocation("minecraft", "adventure/fall_from_world_height")));
        return result;
    }
}
