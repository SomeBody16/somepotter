package network.something.somepotter.spells.spell.protego;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.mechanics.gesture.SpellGesture;
import network.something.somepotter.spells.cast.Cast;
import network.something.somepotter.spells.cast.self.SelfCast;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.spells.spell_type.charm.CharmType;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;

/**
 * Creates a magical barrier that deflects minor to moderate hexes, jinxes, and curses
 * <p>
 * Will work short time and will be able to deflect only small spells
 */
public class ProtegoSpell extends Spell {

    public static final String ID = "protego";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<ProtegoSpell> getListener() {
        return new ProtegoListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new SelfCast();
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Items.SHIELD);
    }

    @Override
    public int getSkillPointCost() {
        return 1;
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.line(50, 90, 50, 10);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }

    @Override
    public Color getColor() {
        return new Color(0x00FFFF);
    }

    @Override
    public @NotNull SpellType getType() {
        return SpellTypeInit.get(CharmType.ID);
    }
}
