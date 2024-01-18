package network.something.somepotter.util;

import com.mojang.math.Vector3f;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.phys.Vec3;

public class ColorUtil {

    public static final ColorUtil TRANSFIGURATION = new ColorUtil(0x7D5BA6);
    public static final ColorUtil CONJURATION = new ColorUtil(0x003366);
    public static final ColorUtil CHARM = new ColorUtil(0xFFC0CB);
    public static final ColorUtil JINX = new ColorUtil(0xFF8C00);
    public static final ColorUtil HEX = new ColorUtil(0x8B0000);
    public static final ColorUtil CURSE = new ColorUtil(0x4B0082);
    public static final ColorUtil COUNTER_SPELL = new ColorUtil(0x808080);
    public static final ColorUtil HEALING = new ColorUtil(0x98FB98);
    public static final ColorUtil UNFORGIVEABLE = new ColorUtil(57, 155, 130);


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

    public int getRGBA24(float alpha) {
        var r = (int) (color.x() * 255F);
        var g = (int) (color.y() * 255F);
        var b = (int) (color.z() * 255F);
        var a = (int) (alpha * 255F);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public int getRGBA24() {
        return getRGBA24(1F);
    }

}
