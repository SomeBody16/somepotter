package network.something.somepotter.spells.spell.reducio;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.mechanics.gesture.SpellGesture;
import network.something.somepotter.spells.cast.Cast;
import network.something.somepotter.spells.cast.projectile_or_self.ProjectileOrSelfCast;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.spells.spell_type.charm.CharmType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReducioSpell extends Spell {
    public static final String ID = "reducio";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<ReducioSpell> getListener() {
        return new ReducioListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileOrSelfCast();
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Items.RED_MUSHROOM);
    }

    @Override
    public int getSkillPointCost() {
        return 5;
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.line(80, 10, 20, 50);
        gesture.draw.line(20, 50, 80, 90);

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
