package network.something.somemagic.util;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ParticleUtil {

    public static void spawn(Level level, ParticleOptions particle, Vec3 pos, int range, int particlesPerBlock) {
        if (level instanceof ServerLevel serverLevel) {
            for (int r = 0; r < (range * 2); ++r) {
                for (int c = 0; c < (range * 2); ++c) {
                    for (int h = 0; h < (range * 2); ++h) {
                        double x = pos.x + r - range;
                        double y = pos.y + h - range;
                        double z = pos.z + c - range;

                        for (int l = 0; l < particlesPerBlock; ++l) {
                            serverLevel.sendParticles(particle,
                                    x + Math.random(), y + Math.random(), z + Math.random(),
                                    1, 0, 0, 0, 0);
                        }
                    }
                }
            }
        }
    }

    public static void inAABB(Level level, ParticleOptions particle, AABB areaOfEffect, int particlesPerBlock) {
        if (level instanceof ServerLevel serverLevel) {
            double minX = areaOfEffect.minX;
            double minY = areaOfEffect.minY;
            double minZ = areaOfEffect.minZ;
            double maxX = areaOfEffect.maxX;
            double maxY = areaOfEffect.maxY;
            double maxZ = areaOfEffect.maxZ;

            for (double x = minX; x <= maxX; x += 0.2) {
                for (double y = minY; y <= maxY; y += 0.2) {
                    for (double z = minZ; z <= maxZ; z += 0.2) {
                        for (int l = 0; l < particlesPerBlock; ++l) {
                            serverLevel.sendParticles(particle,
                                    x + Math.random(), y + Math.random(), z + Math.random(),
                                    1, 0, 0, 0, 0);
                        }
                    }
                }
            }
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
                        1, 0, 0, 0, 0);
            }
        }
    }

}
