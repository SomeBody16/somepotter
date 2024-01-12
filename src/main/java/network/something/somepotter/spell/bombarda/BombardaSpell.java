package network.something.somepotter.spell.bombarda;

import net.minecraft.world.level.Explosion;
import network.something.somepotter.cast.Cast;
import network.something.somepotter.cast.projectile.ProjectileCast;
import network.something.somepotter.gesture.SpellGesture;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.util.ColorUtil;
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
    public ColorUtil getColor() {
        return ColorUtil.DAMAGE;
    }
}
