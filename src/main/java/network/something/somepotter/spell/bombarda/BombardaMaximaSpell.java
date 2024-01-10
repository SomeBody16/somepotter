package network.something.somepotter.spell.bombarda;

import net.minecraft.world.level.Explosion;

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
}
