package network.something.somepotter.spells.spell_type.conjuration;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.util.ColorUtil;

public class ConjurationType extends SpellType {
    public static String ID = "conjuration";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public ColorUtil getColor() {
        return ColorUtil.CONJURATION;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Items.PARROT_SPAWN_EGG);
    }

    @Override
    public int getBaseSkillPointCost() {
        return 2;
    }
}
