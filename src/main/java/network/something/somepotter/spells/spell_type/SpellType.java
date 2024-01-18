package network.something.somepotter.spells.spell_type;


import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import network.something.somepotter.util.ColorUtil;

public abstract class SpellType {

    abstract public String getId();

    abstract public int getBaseSkillPointCost();

    public ColorUtil getColor() {
        return new ColorUtil(0, 0, 0);
    }

    public ParticleOptions getParticle() {
        return getColor().getParticle();
    }

    public ItemStack getIcon() {
        return new ItemStack(Items.ENCHANTED_BOOK);
    }

}
