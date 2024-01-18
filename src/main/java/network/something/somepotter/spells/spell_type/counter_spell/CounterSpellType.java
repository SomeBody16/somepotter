package network.something.somepotter.spells.spell_type.counter_spell;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.util.ColorUtil;

public class CounterSpellType extends SpellType {
    public static String ID = "counter_spell";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public ColorUtil getColor() {
        return ColorUtil.COUNTER_SPELL;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Items.SHIELD);
    }

    @Override
    public int getBaseSkillPointCost() {
        return 4;
    }
}
