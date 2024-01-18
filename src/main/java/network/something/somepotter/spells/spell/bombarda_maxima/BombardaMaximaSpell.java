package network.something.somepotter.spells.spell.bombarda_maxima;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import network.something.somepotter.mechanics.gesture.SpellGesture;
import network.something.somepotter.spells.requirement.Requirement;
import network.something.somepotter.spells.requirement.near_explosions.NearExplosionsRequirement;
import network.something.somepotter.spells.requirement.spell_learned.SpellLearnedRequirement;
import network.something.somepotter.spells.requirement.spell_point.SpellPointRequirement;
import network.something.somepotter.spells.spell.bombarda.BombardaSpell;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Explodes the target (large explosion)
 * <p>
 * Will destroy blocks
 */
public class BombardaMaximaSpell extends BombardaSpell {
    public static final String ID = "bombarda_maxima";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public float getAreaOfEffect() {
        return 6.0F;
    }

    @Override
    public ItemStack getIcon() {
        var item = super.getIcon();
        item.enchant(Enchantments.POWER_ARROWS, 1);
        return item;
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.line(60, 23, 50, 40);
        gesture.draw.line(50, 40, 70, 75);
        gesture.draw.line(70, 75, 30, 75);
        gesture.draw.line(30, 75, 50, 40);
        gesture.removeFromEnd(10);

        gesture.nextStroke();
        gesture.draw.circle(50, 52, 37);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return super.getMistakes();
    }

    @Override
    public List<Requirement> getRequirements() {
        var result = new ArrayList<Requirement>();
        result.add(SpellPointRequirement.of(getSkillPointCost()));
        result.add(NearExplosionsRequirement.of(10, 6.0));
        result.add(SpellLearnedRequirement.of(BombardaSpell.ID));
        return result;
    }
}
