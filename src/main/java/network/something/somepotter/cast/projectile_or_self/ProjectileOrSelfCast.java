package network.something.somepotter.cast.projectile_or_self;

import network.something.somepotter.cast.Cast;
import network.something.somepotter.cast.projectile.ProjectileCast;
import network.something.somepotter.cast.self.SelfCast;

public class ProjectileOrSelfCast extends Cast {
    public static final String ID = "projectile_or_self";

    protected ProjectileCast projectileCast = new ProjectileCast();
    protected SelfCast selfCast = new SelfCast();

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void execute() {
        if (caster.isCrouching()) {
            selfCast.execute();
            return;
        }

        projectileCast.execute();
    }

    public ProjectileOrSelfCast setRange(int range) {
        projectileCast.setRange(range);
        return this;
    }

    public ProjectileOrSelfCast setVelocity(float velocity) {
        projectileCast.setVelocity(velocity);
        return this;
    }

    public ProjectileOrSelfCast canHitFluid(boolean canHitFluid) {
        projectileCast.canHitFluid(canHitFluid);
        return this;
    }

    public ProjectileOrSelfCast canHitFluid() {
        return canHitFluid(true);
    }

}
