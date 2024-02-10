package network.something.somepotter.effect;

import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import network.something.somepotter.effect.client.LightClient;
import network.something.somepotter.effect.client.ParticleClient;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
class EffectsClient {

    public static void protegoDiabolica(Level level, Vec3 pos, Color startColor, Color endColor) {
        ParticleClient.protegoDiabolica(level, pos, startColor, endColor);
    }

    public static void incarcerousCaptura(Level level, Vec3 caster, Vec3 target, Color color) {
        ParticleClient.incarcerousCaptura(level, caster, target, color);
    }

    public static void chunkHighlight(Level level, BlockPos pos, Color startColor, Color endColor) {
        ParticleClient.chunkHighlight(level, pos, startColor, endColor);
    }

    public static void touch(Level level, Vec3 pos, Color startColor, Color endColor) {
        ParticleClient.touch(level, pos, startColor, endColor);
        LightClient.explosion(20, new Vector3f(pos), startColor, 8);
    }

    public static void teleport(Level level, Vec3 pos, Color startColor, Color endColor) {
        ParticleClient.touch(level, pos, startColor, endColor);
    }

}
