package network.something.somepotter.util;

import com.mojang.math.Vector3f;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.phys.Vec3;

public class SpellColor {

    public static final SpellColor FORCE = new SpellColor(123, 97, 167);
    public static final SpellColor DAMAGE = new SpellColor(128, 27, 4);
    public static final SpellColor UNFORGIVEABLE = new SpellColor(57, 155, 130);
    ;
    public static final SpellColor ESSENTIAL = new SpellColor(50, 46, 43);
    public static final SpellColor TRANSFIGURATION = new SpellColor(0x69ff7d);
    public static final SpellColor UTILITY = new SpellColor(132, 159, 173);
    public static final SpellColor CONTROL = new SpellColor(171, 146, 34);

    public Vector3f color;

    public SpellColor(int red, int green, int blue) {
        this(new Vector3f(red / 255F, green / 255F, blue / 255F));
    }

    public SpellColor(int color) {
        this(new Vector3f(Vec3.fromRGB24(color)));
    }

    public SpellColor(Vector3f color) {
        this.color = color;
    }

    public DustParticleOptions getParticle() {
        return new DustParticleOptions(color, 1.0F);
    }
}
