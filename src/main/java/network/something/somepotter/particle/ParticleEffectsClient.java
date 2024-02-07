package network.something.somepotter.particle;

import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
class ParticleEffectsClient {

    public static void protegoDiabolica(Level level, Vec3 pos, Color startColor, Color endColor) {
        spawnParticles(level, pos, startColor, endColor, 2, 0.001, 1);
    }

    public static void incarcerousCaptura(Level level, Vec3 caster, Vec3 target, Color color) {
        spawnParticles(level, target, color, color.darker(), 5, 0.5, 1);
    }

    public static void chunkHighlight(Level level, BlockPos pos, Color startColor, Color endColor) {
        final int maxYDifference = 2; // Max height difference to consider a block as a surface
        final int chunkSize = 16; // Size of a chunk in blocks

        // Iterate over the (x, z) coordinates of the chunk that contains the provided BlockPos
        for (int x = (pos.getX() - chunkSize / 2); x <= (pos.getX() + chunkSize / 2); x++) {
            for (int z = (pos.getZ() - chunkSize / 2); z <= (pos.getZ() + chunkSize / 2); z++) {
                // Check blocks from pos.y - maxYDifference to pos.y + maxYDifference
                for (int y = pos.getY() - maxYDifference; y <= pos.getY() + maxYDifference; y++) {
                    var spawnPos = new Vec3(x + 0.5, y + 1, z + 0.5);
                    var spawnBlockPos = new BlockPos(spawnPos);

                    var isCurrentBlockAir = level.getBlockState(spawnBlockPos).isAir();
                    var isBlockBelowSolid = !level.getBlockState(spawnBlockPos.below()).isAir();

                    if (isCurrentBlockAir && isBlockBelowSolid) {
                        spawnParticles(level, spawnPos, startColor, endColor, 5, 1, 1);
                    }
                }
            }
        }
    }

    public static void touch(Level level, Vec3 pos, Color startColor, Color endColor) {
        spawnParticles(level, pos, startColor, endColor, 100, 1.5, 2.5);
        level.addParticle(ParticleTypes.FLASH, true, pos.x, pos.y, pos.z, 0, 0, 0);
        level.addParticle(ParticleTypes.POOF, true, pos.x, pos.y, pos.z, 0, 0, 0);
        level.addParticle(ParticleTypes.SCRAPE, true, pos.x, pos.y, pos.z, 0, 0, 0);
    }

    public static void teleport(Level level, Vec3 pos, Color startColor, Color endColor) {
        spawnParticles(level, pos, startColor, endColor, 500, 0.8, 3);
    }

    protected static void spawnParticles(Level level, Vec3 pos, Color startColor, Color endColor, int particleCount, double velocityMultiplier, double offsetRange) {
        for (int i = 0; i < particleCount; i++) {
            // Interpolate color between startColor and endColor
            float factor = i / (float) particleCount;
            int r = (int) (startColor.getRed() * (1 - factor) + endColor.getRed() * factor);
            int g = (int) (startColor.getGreen() * (1 - factor) + endColor.getGreen() * factor);
            int b = (int) (startColor.getBlue() * (1 - factor) + endColor.getBlue() * factor);

            var particleOptions = getParticle(new Color(r, g, b));

            // Randomize the velocity for each particle to spread them out in different directions
            double velX = (Math.random() - 0.5) * velocityMultiplier;
            double velY = (Math.random() - 0.5) * velocityMultiplier;
            double velZ = (Math.random() - 0.5) * velocityMultiplier;

            // Randomize the spawn position within 1 block range of the impact point
            double offsetX = pos.x + (Math.random() - 0.5) * offsetRange;
            double offsetY = pos.y + (Math.random() - 0.5) * offsetRange;
            double offsetZ = pos.z + (Math.random() - 0.5) * offsetRange;

            // Add the particle to the world
            level.addParticle(particleOptions, true, offsetX, offsetY, offsetZ, velX, velY, velZ);
        }
    }

    protected static ParticleOptions getParticle(Color color) {
        var particleColorVec = new Vector3f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
        return new DustParticleOptions(particleColorVec, 1F);
    }

}
