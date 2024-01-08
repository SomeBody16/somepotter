package network.something.somepotter.cast.touch;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spell.Spell;

public class TouchCast {

    public static void playParticles(ParticleOptions particle, ServerLevel level, Vec3 pos) {
        level.sendParticles(
                particle,
                pos.x, pos.y, pos.z,
                40,
                1, 1, 1,
                0.01
        );
    }

    public LivingEntity caster;
    public ServerLevel level;
    public Spell spell;

    public int abilityPower;
    public float areaOfEffect;
    public int range = 32;
    public boolean canHitFluid = false;

    public TouchCast(SpellCastEvent<?> event) {
        this.caster = event.caster;
        this.level = event.level;
        this.spell = event.spell;
        this.abilityPower = event.abilityPower;
        this.areaOfEffect = event.areaOfEffect;
    }

    public void execute() {
        var result = caster.pick(range, 1.0F, canHitFluid);

        var cancelled = SpellHitEvent.publish(spell, caster, level, abilityPower, areaOfEffect, result);
        if (!cancelled) {
            playParticles(spell.getParticle(), level, result.getLocation());
        }
    }

    public TouchCast setRange(int range) {
        this.range = range;
        return this;
    }

    public TouchCast canHitFluid() {
        this.canHitFluid = true;
        return this;
    }

}
