package network.something.somepotter.spells.spell_type.jinx;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import network.something.somepotter.spells.spell_type.SpellType;

import java.awt.*;

public class JinxType extends SpellType {
    public static String ID = "jinx";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Color getColor() {
        return new Color(0xFF8C00);
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Items.POISONOUS_POTATO);
    }

    @Override
    public int getBaseSkillPointCost() {
        return 6;
    }
}
