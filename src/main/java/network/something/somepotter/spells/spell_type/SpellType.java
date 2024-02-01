package network.something.somepotter.spells.spell_type;


import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.awt.*;

public abstract class SpellType {

    abstract public String getId();

    abstract public int getBaseSkillPointCost();

    public Color getColor() {
        return new Color(0, 0, 0);
    }

    public ItemStack getIcon() {
        return new ItemStack(Items.ENCHANTED_BOOK);
    }

}
