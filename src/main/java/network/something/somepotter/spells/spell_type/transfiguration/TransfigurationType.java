package network.something.somepotter.spells.spell_type.transfiguration;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import network.something.somepotter.spells.spell_type.SpellType;

import java.awt.*;

public class TransfigurationType extends SpellType {

    public static final String ID = "transfiguration";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Color getColor() {
        return new Color(0x7D5BA6);
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Items.CRAFTING_TABLE);
    }

    @Override
    public int getBaseSkillPointCost() {
        return 3;
    }
}
