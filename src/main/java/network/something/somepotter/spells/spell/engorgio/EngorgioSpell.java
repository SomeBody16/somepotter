package network.something.somepotter.spells.spell.engorgio;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.mechanics.gesture.SpellGesture;
import network.something.somepotter.spells.cast.Cast;
import network.something.somepotter.spells.cast.projectile_or_self.ProjectileOrSelfCast;
import network.something.somepotter.spells.requirement.Requirement;
import network.something.somepotter.spells.requirement.spell_learned.SpellLearnedRequirement;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell.reducio.ReducioSpell;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.spells.spell_type.charm.CharmType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EngorgioSpell extends Spell {

    public static final String ID = "engorgio";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<EngorgioSpell> getListener() {
        return new EngorgioListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileOrSelfCast();
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Items.RED_MUSHROOM_BLOCK);
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.line(20, 10, 80, 50);
        gesture.draw.line(80, 50, 20, 90);

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
        result.add(SpellLearnedRequirement.of(ReducioSpell.ID));
        return result;
    }
}
