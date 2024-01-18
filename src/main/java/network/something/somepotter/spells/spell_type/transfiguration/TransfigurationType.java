package network.something.somepotter.spells.spell_type.transfiguration;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.util.ColorUtil;

public class TransfigurationType extends SpellType {

    public static final String ID = "transfiguration";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public ColorUtil getColor() {
        return ColorUtil.TRANSFIGURATION;
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
