package network.something.somepotter.spells.spell_type.curse;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import network.something.somepotter.spells.spell_type.SpellType;

import java.awt.*;

public class CurseType extends SpellType {
    public static String ID = "curse";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Color getColor() {
        return new Color(0x4B0082);
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Items.WITHER_ROSE);
    }

    @Override
    public int getBaseSkillPointCost() {
        return 15;
    }
}
