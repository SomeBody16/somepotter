package network.something.somepotter.magic.spell;

import net.minecraft.world.entity.LivingEntity;

public class BombardaMaximaSpell extends BombardaSpell {
    public static final String ID = "bombarda_maxima";

    public BombardaMaximaSpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public int getCooldownTicks() {
        return 20 * 16;
    }

    @Override
    public float getExplosionRadius() {
        return 8.0f;
    }

}

