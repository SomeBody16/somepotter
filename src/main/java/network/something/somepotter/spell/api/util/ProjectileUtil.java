package network.something.somepotter.spell.api.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ProjectileUtil {

    public static void castProjectile(LivingEntity caster, Entity spellProjectile, int range, boolean canHitFluid, float speed) {
        Vec3 lookVec = caster.getLookAngle();
        Vec3 handPos = new Vec3(
                caster.getX() + 0.25,
                caster.getY() + (caster.getEyeHeight() * 0.65),
                caster.getZ()
        );
        spellProjectile.setPos(handPos);

        HitResult hitResult = caster.pick(range, 1.0F, canHitFluid);
        if (hitResult.getType() == HitResult.Type.MISS) {
            // If no target was found, shoot in the direction the caster is looking at
            spellProjectile.setDeltaMovement(lookVec.normalize());
        } else {
            Vec3 targetVec = hitResult.getLocation().subtract(handPos);
            spellProjectile.setDeltaMovement(targetVec.normalize());
        }

        Vec3 speedVector = new Vec3(speed, speed, speed);
        Vec3 newDeltaMovement = spellProjectile.getDeltaMovement().multiply(speedVector);
        spellProjectile.setDeltaMovement(newDeltaMovement);

        caster.getLevel().addFreshEntity(spellProjectile);
    }

}
