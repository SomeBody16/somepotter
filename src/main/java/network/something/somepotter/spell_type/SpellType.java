package network.something.somepotter.spell_type;


import net.minecraft.core.particles.ParticleOptions;
import network.something.somepotter.util.ColorUtil;

public abstract class SpellType {

    public abstract String getId();

    public ColorUtil getColor() {
        return new ColorUtil(0, 0, 0);
    }

    public ParticleOptions getParticle() {
        return getColor().getParticle();
    }

}
