package network.something.somepotter.spell.bombarda_maxima;

import net.minecraft.world.level.Explosion;
import network.something.somepotter.gesture.SpellGesture;
import network.something.somepotter.spell.bombarda.BombardaSpell;
import org.jetbrains.annotations.NotNull;

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
    public Explosion.BlockInteraction getBlockInteraction() {
        return Explosion.BlockInteraction.BREAK;
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
}
