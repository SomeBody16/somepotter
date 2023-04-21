package network.something.somepotter.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ParticleUtil {

    public static void spawn(Level level, ParticleOptions particle, Vec3 pos, int range, int particlesPerBlock) {
        var aof = new AABB(new BlockPos(pos)).inflate(range);
        var amount = (int) (aof.getXsize() * aof.getYsize() * aof.getZsize() * particlesPerBlock);
        inAABBOfAmount(level, particle, aof, amount);

        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    particle,
                    pos.x, pos.y, pos.z,
                    amount,
                    range, range, range,
                    0.01
            );
        }
    }

    public static void inAABBOfAmount(Level level, ParticleOptions particle, AABB areaOfEffect, int particleAmount) {
        if (level instanceof ServerLevel serverLevel) {
            double x = level.random.nextDouble(areaOfEffect.minX, areaOfEffect.maxX);
            double y = level.random.nextDouble(areaOfEffect.minY, areaOfEffect.maxY);
            double z = level.random.nextDouble(areaOfEffect.minZ, areaOfEffect.maxZ);

            for (int i = 0; i < particleAmount; i++) {
                serverLevel.sendParticles(particle,
                        x, y, z,
                        1, 3, 3, 3, 0.025);
            }
        }
    }

}
