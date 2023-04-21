package network.something.somemagic.magic.spell.core;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somemagic.entity.SpellEntity;

public abstract class ProjectileOrSelfSpell extends ProjectileSpell {

    protected ProjectileOrSelfSpell(String name, LivingEntity caster) {
        super(name, caster);
    }

    public abstract void onHitEntity(SpellEntity spellEntity, EntityHitResult hitResult);

    public abstract void onHitBlock(SpellEntity spellEntity, BlockHitResult hitResult);

    public abstract void onCastSelf();

    public double getSpeed() {
        return 3.0;
    }

    public ParticleOptions getParticle() {
        return getColor().getParticle();
    }

    public void cast() {
        if (caster.isCrouching()) {
            ServerLevel level = (ServerLevel) caster.level;
            level.sendParticles(
                    getColor().getParticle(),
                    caster.getX(), caster.getY(), caster.getZ(),
                    25,
                    1, 1, 1,
                    0.01
            );
            this.onCastSelf();
            return;
        }
        super.cast();
    }
}
