package network.something.somepotter.spells.spell.bombarda;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.mechanics.gesture.SpellGesture;
import network.something.somepotter.spells.cast.Cast;
import network.something.somepotter.spells.cast.projectile.ProjectileCast;
import network.something.somepotter.spells.requirement.Requirement;
import network.something.somepotter.spells.requirement.near_explosions.NearExplosionsRequirement;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.spells.spell_type.charm.CharmType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Explodes the target (small explosion)
 * <p>
 * No damage to blocks
 */
public class BombardaSpell extends Spell {

    public static final String ID = "bombarda";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public float getAreaOfEffect() {
        return 2.0F;
    }

    public Explosion.BlockInteraction getBlockInteraction() {
        return Explosion.BlockInteraction.NONE;
    }

    @Override
    public @NotNull() SpellListener<BombardaSpell> getListener() {
        return new BombardaListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileCast();
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Items.TNT);
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.line(60, 23, 50, 40);
        gesture.draw.line(50, 40, 70, 75);
        gesture.draw.line(70, 75, 30, 75);
        gesture.draw.line(30, 75, 50, 40);
        gesture.removeFromEnd(10);

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
        result.add(NearExplosionsRequirement.of(3, 5));
        return result;
    }
}
