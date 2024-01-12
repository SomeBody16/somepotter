package network.something.somepotter.cast.projectile;

import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.cast.Cast;

public class ProjectileCast extends Cast {
    public static final String ID = "projectile";

    protected int range = 32;
    protected boolean canHitFluid = false;
    protected float velocity = 1.5F;

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void execute() {
        var projectile = new ProjectileCastEntity(ProjectileCastEntity.TYPE,
                level, caster, spell.getId(), abilityPower, areaOfEffect, range);

        var handPos = new Vec3(
                caster.getX(),
                caster.getY() + (caster.getEyeHeight() * 0.65),
                caster.getZ()
        );
        projectile.setPos(handPos);

        var lookVec = caster.getLookAngle();
        var hitResult = caster.pick(range, 1.0F, canHitFluid);
        if (hitResult.getType() == HitResult.Type.MISS) {
            // If no target was found, shoot in the direction the caster is looking at
            projectile.setDeltaMovement(lookVec.normalize());
        } else {
            var targetVec = hitResult.getLocation().subtract(handPos).normalize();
            projectile.setDeltaMovement(targetVec);
        }

        var speedVector = new Vec3(velocity, velocity, velocity);
        var newDeltaMovement = projectile.getDeltaMovement().multiply(speedVector);
        projectile.setDeltaMovement(newDeltaMovement);

        level.addFreshEntity(projectile);
    }

    public ProjectileCast setRange(int range) {
        this.range = range;
        return this;
    }

    public ProjectileCast setVelocity(float velocity) {
        this.velocity = velocity;
        return this;
    }

    public ProjectileCast canHitFluid(boolean canHitFluid) {
        this.canHitFluid = canHitFluid;
        return this;
    }

    public ProjectileCast canHitFluid() {
        return canHitFluid(true);
    }

}
