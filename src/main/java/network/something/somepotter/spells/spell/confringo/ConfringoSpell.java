package network.something.somepotter.spells.spell.confringo;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.mechanics.gesture.SpellGesture;
import network.something.somepotter.spells.cast.Cast;
import network.something.somepotter.spells.cast.projectile.ProjectileCast;
import network.something.somepotter.spells.requirement.Requirement;
import network.something.somepotter.spells.requirement.holding_item.HoldingItemRequirement;
import network.something.somepotter.spells.requirement.spell_learned.SpellLearnedRequirement;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell.bombarda.BombardaSpell;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.spells.spell_type.charm.CharmType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Explodes the target (small explosion)
 * <p>
 * Will not destroy blocks
 * Small damage, cause fire (this fire will deal more damage)
 */
public class ConfringoSpell extends Spell {
    public static final String ID = "confringo";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<ConfringoSpell> getListener() {
        return new ConfringoListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileCast();
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Items.FIRE_CHARGE);
    }

    @Override
    public float getAreaOfEffect() {
        return 4.0F;
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.line(20, 20, 80, 20);
        gesture.draw.line(80, 20, 20, 80);
        gesture.draw.line(20, 80, 80, 80);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }

    @Override
    public @NotNull SpellType getType() {
        return SpellTypeInit.get(CharmType.ID);
    }

    @Override
    public List<Requirement> getRequirements() {
        var result = super.getRequirements();
        result.add(SpellLearnedRequirement.of(BombardaSpell.ID));
        result.add(HoldingItemRequirement.of(Items.FIRE_CHARGE, 1, true));
        return result;
    }
}
