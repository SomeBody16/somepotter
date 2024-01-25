package network.something.somepotter.spells.spell.apparition;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import network.something.somepotter.mechanics.gesture.SpellGesture;
import network.something.somepotter.spells.cast.Cast;
import network.something.somepotter.spells.cast.self.SelfCast;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.spells.spell_type.charm.CharmType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ApparitionSpell extends Spell {
    public static final String ID = "apparition";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<ApparitionSpell> getListener() {
        return new ApparitionListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new SelfCast();
    }

    @Override
    public @NotNull SpellType getType() {
        return new CharmType();
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.circle(20, 70, 10);
        gesture.nextStroke();
        gesture.draw.bezierCurve(20, 55, 40, 35, 60, 35, 80, 55);
        gesture.nextStroke();
        gesture.draw.circle(80, 70, 10);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }

    @Override
    public int getSkillPointCost() {
        return 3;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Items.ENDER_PEARL);
    }
}
