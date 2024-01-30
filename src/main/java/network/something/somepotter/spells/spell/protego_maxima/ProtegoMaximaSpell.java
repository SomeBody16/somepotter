package network.something.somepotter.spells.spell.protego_maxima;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.mechanics.gesture.SpellGesture;
import network.something.somepotter.spells.cast.Cast;
import network.something.somepotter.spells.cast.projectile.ProjectileCast;
import network.something.somepotter.spells.requirement.Requirement;
import network.something.somepotter.spells.requirement.spell_learned.SpellLearnedRequirement;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell.protego.ProtegoSpell;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.spells.spell_type.charm.CharmType;
import network.something.somepotter.util.ColorUtil;
import network.something.somepotter.util.ConfigUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

/**
 * Claims a chunk, preventing other players from breaking blocks in it
 * Every spell cast in the chunk will be reported to owners
 */
public class ProtegoMaximaSpell extends Spell {
    public static final String ID = "protego_maxima";
    public static final Supplier<ProtegoMaximaConfig> CONFIG = ConfigUtil.server(ProtegoMaximaConfig.class, ID);

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<ProtegoMaximaSpell> getListener() {
        return new ProtegoMaximaListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileCast();
    }

    @Override
    public int getSkillPointCost() {
        return 1;
    }

    @Override
    public ItemStack getIcon() {
        var item = new ItemStack(Items.SHIELD);
        item.enchant(Enchantments.POWER_ARROWS, 1);
        return item;
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.line(50, 90, 50, 10);

        gesture.nextStroke();
        gesture.draw.circle(50, 50, 45);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }

    @Override
    public void register() {
        super.register();
    }

    @Override
    public @NotNull SpellType getType() {
        return SpellTypeInit.get(CharmType.ID);
    }

    @Override
    public ColorUtil getColor() {
        return new ColorUtil(0xFF00FF);
    }

    @Override
    public List<Requirement> getRequirements() {
        var result = super.getRequirements();
        result.add(SpellLearnedRequirement.of(ProtegoSpell.ID));
        return result;
    }
}
