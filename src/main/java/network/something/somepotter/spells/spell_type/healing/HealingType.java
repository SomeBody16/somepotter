package network.something.somepotter.spells.spell_type.healing;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import network.something.somepotter.spells.spell_type.SpellType;

import java.awt.*;

public class HealingType extends SpellType {
    public static String ID = "healing";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Color getColor() {
        return new Color(0x98FB98);
    }

    @Override
    public ItemStack getIcon() {
        return PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.HEALING);
    }

    @Override
    public int getBaseSkillPointCost() {
        return 5;
    }
}
