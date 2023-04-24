package network.something.somepotter.spell.api.particle;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;

public class SpellParticle {

    private static final Map<String, ParticleOptions> PARTICLES = new HashMap<>();

    public static void register(String spellId, ParticleOptions particle) {
        PARTICLES.put(spellId, particle);
    }

    public static void playTrailParticles(String spellId, Level level, Entity projectile) {
        if (!PARTICLES.containsKey(spellId)) {
            return;
        }
        var particle = PARTICLES.get(spellId);

        for (int i = 0; i < 1; i++) {
            double deltaX = projectile.getX() - projectile.xOld;
            double deltaY = projectile.getY() - projectile.yOld;
            double deltaZ = projectile.getZ() - projectile.zOld;
            double dist = Math.ceil(Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) * 6);
            for (double j = 0; j < dist; j++) {
                double coeff = j / dist;
                level.addParticle(particle,
                        (float) (projectile.xo + deltaX * coeff),
                        (float) (projectile.yo + deltaY * coeff) + 0.1, (float)
                                (projectile.zo + deltaZ * coeff),
                        0.0125f * (level.random.nextFloat() - 0.5f),
                        0.0125f * (level.random.nextFloat() - 0.5f),
                        0.0125f * (level.random.nextFloat() - 0.5f));
            }
        }
    }

    public static void playTouchParticles(String spellId, ServerLevel level, Vec3 pos) {
        if (!PARTICLES.containsKey(spellId)) {
            return;
        }
        var particle = PARTICLES.get(spellId);

        level.sendParticles(
                particle,
                pos.x, pos.y, pos.z,
                40,
                1, 1, 1,
                0.01
        );
    }

}
