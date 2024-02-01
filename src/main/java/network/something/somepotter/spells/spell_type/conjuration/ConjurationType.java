package network.something.somepotter.spells.spell_type.conjuration;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import network.something.somepotter.spells.spell_type.SpellType;

import java.awt.*;

public class ConjurationType extends SpellType {
    public static String ID = "conjuration";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Color getColor() {
        return new Color(0x003366);
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
