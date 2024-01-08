package network.something.somepotter.util;

import com.mojang.math.Vector3f;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.phys.Vec3;

public class ColorUtil {

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

    public DustParticleOptions getParticle() {
        return new DustParticleOptions(color, 1F);
    }

    public int getRGB24() {
        var r = (int) (color.x() * 255F);
        var g = (int) (color.y() * 255F);
        var b = (int) (color.z() * 255F);
        return (r << 16) | (g << 8) | b;
    }

}
