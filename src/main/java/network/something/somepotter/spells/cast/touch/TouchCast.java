package network.something.somepotter.spells.cast.touch;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.particle.ParticleEffects;
import network.something.somepotter.spells.cast.Cast;

public class TouchCast extends Cast {
    public static final String ID = "touch";

    public static void playParticles(ParticleOptions particle, ServerLevel level, Vec3 pos) {
        level.sendParticles(
                particle,
                pos.x, pos.y, pos.z,
                40,
                1, 1, 1,
                0.01
        );
    }

    public int range = 32;
    public boolean canHitFluid = false;

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void execute() {
        var result = caster.pick(range, 1.0F, canHitFluid);

        var cancelled = SpellHitEvent.publish(spell, caster, level, abilityPower, areaOfEffect, result);
        if (!cancelled) {
            ParticleEffects.touch(level, result.getLocation(), spell.getColor());
        }
    }

    public TouchCast setRange(int range) {
        this.range = range;
        return this;
    }

    public TouchCast canHitFluid(boolean canHitFluid) {
        this.canHitFluid = canHitFluid;
        return this;
    }

    public TouchCast canHitFluid() {
        return canHitFluid(true);
    }

}
