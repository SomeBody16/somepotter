package network.something.somepotter.spells.spell_type.hex;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import network.something.somepotter.spells.spell_type.SpellType;

import java.awt.*;

public class HexType extends SpellType {
    public static String ID = "hex";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Color getColor() {
        return new Color(0x8B0000);
    }

    @Override
    public ItemStack getIcon() {
        return PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WEAKNESS);
    }

    @Override
    public int getBaseSkillPointCost() {
        return 10;
    }
}
