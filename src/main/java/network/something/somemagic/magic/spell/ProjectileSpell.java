package network.something.somemagic.magic.spell;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import network.something.somemagic.entity.SpellEntity;
import network.something.somemagic.init.EntityInit;

public abstract class ProjectileSpell extends Spell {

    protected ProjectileSpell(String name, LivingEntity caster) {
        super(name, caster);
    }

    public double getSpeed() {
        return 3.0;
    }

    public ParticleOptions getParticle() {
        return getColor().getParticle();
    }

    public abstract void onHitEntity(SpellEntity spellEntity, EntityHitResult hitResult);

    public abstract void onHitBlock(SpellEntity spellEntity, BlockHitResult hitResult);

    public void cast() {
        super.cast();

        if (!caster.getLevel().isClientSide()) {
            SpellEntity spellEntity = new SpellEntity(EntityInit.SPELL.get(), caster.level, caster);
            spellEntity.setSpell(this.name);

            Vec3 lookVec = caster.getLookAngle();
            Vec3 handPos = new Vec3(
                    caster.getX(),
                    caster.getY() + (caster.getEyeHeight() * 0.65),
                    caster.getZ()
            );
            spellEntity.setPos(handPos);

            HitResult hitResult = getCasterPOVHitResult();
            if (hitResult.getType() == HitResult.Type.MISS) {
                // If no target was found, shoot in the direction the player is looking at
                spellEntity.setDeltaMovement(lookVec.normalize());
            } else {
                Vec3 targetVec = hitResult.getLocation().subtract(handPos);
                spellEntity.setDeltaMovement(targetVec.normalize());
            }

            Vec3 speedVector = new Vec3(getSpeed(), getSpeed(), getSpeed());
            Vec3 newDeltaMovement = spellEntity.getDeltaMovement().multiply(speedVector);
            spellEntity.setDeltaMovement(newDeltaMovement);

            caster.getLevel().addFreshEntity(spellEntity);
        }
    }

    public void playTrailParticles(SpellEntity spellEntity) {
        for (int i = 0; i < 1; i++) {
            double deltaX = spellEntity.getX() - spellEntity.xOld;
            double deltaY = spellEntity.getY() - spellEntity.yOld;
            double deltaZ = spellEntity.getZ() - spellEntity.zOld;
            double dist = Math.ceil(Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) * 6);
            for (double j = 0; j < dist; j++) {
                double coeff = j / dist;
                spellEntity.level.addParticle(getParticle(),
                        (float) (spellEntity.xo + deltaX * coeff),
                        (float) (spellEntity.yo + deltaY * coeff) + 0.1, (float)
                                (spellEntity.zo + deltaZ * coeff),
                        0.0125f * (caster.level.random.nextFloat() - 0.5f),
                        0.0125f * (caster.level.random.nextFloat() - 0.5f),
                        0.0125f * (caster.level.random.nextFloat() - 0.5f));
            }
        }
    }
}
