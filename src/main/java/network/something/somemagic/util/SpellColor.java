package network.something.somemagic.util;

import com.mojang.math.Vector3f;
import net.minecraft.core.particles.DustParticleOptions;

public class SpellColor {

    public static final SpellColor FORCE = new SpellColor(123, 97, 167);
    public static final SpellColor DAMAGE = new SpellColor(128, 27, 4);
    public static final SpellColor UNFORGIVEABLE = new SpellColor(57, 155, 130);
    ;
    public static final SpellColor ESSENTIAL = new SpellColor(50, 46, 43);
    public static final SpellColor TRANSFIGURATION = new SpellColor(137, 144, 124);
    public static final SpellColor UTILITY = new SpellColor(132, 159, 173);
    public static final SpellColor CONTROL = new SpellColor(171, 146, 34);

    public int red;
    public int green;
    public int blue;

    public SpellColor(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public DustParticleOptions getParticle() {
        Vector3f color = new Vector3f(red / 255F, green / 255F, blue / 255F);
        return new DustParticleOptions(color, 1.0F);
    }
}
