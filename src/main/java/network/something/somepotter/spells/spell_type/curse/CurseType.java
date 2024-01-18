package network.something.somepotter.spells.spell_type.curse;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.util.ColorUtil;

public class CurseType extends SpellType {
    public static String ID = "curse";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public ColorUtil getColor() {
        return ColorUtil.CURSE;
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
