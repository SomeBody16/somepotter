package network.something.somepotter.util;

import com.mojang.math.Vector3f;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.phys.Vec3;

public class ColorUtil {

    public static final ColorUtil FORCE = new ColorUtil(123, 93, 167);
    public static final ColorUtil DAMAGE = new ColorUtil(128, 27, 4);
    public static final ColorUtil UNFORGIVEABLE = new ColorUtil(57, 155, 130);
    public static final ColorUtil ESSENTIAL = new ColorUtil(50, 46, 43);
    public static final ColorUtil TRANSFIGURATION = new ColorUtil(0x69FF7D);
    public static final ColorUtil UTILITY = new ColorUtil(132, 159, 173);
    public static final ColorUtil CONTROL = new ColorUtil(171, 146, 34);


    protected Vector3f color;

    public ColorUtil(Vector3f color) {
        this.color = color;
    }

    public ColorUtil(int red, int green, int blue) {
        this(new Vector3f(red / 255F, green / 255F, blue / 255F));
    }

    public ColorUtil(int hex) {
        this(new Vector3f(Vec3.fromRGB24(hex)));
    }

    public ParticleOptions getParticle() {
//        return ParticleTypes.ENTITY_EFFECT;
        return new DustParticleOptions(color, 1F);
    }

    public int getRGB24() {
        var r = (int) (color.x() * 255F);
        var g = (int) (color.y() * 255F);
        var b = (int) (color.z() * 255F);
        return (r << 16) | (g << 8) | b;
    }

}
