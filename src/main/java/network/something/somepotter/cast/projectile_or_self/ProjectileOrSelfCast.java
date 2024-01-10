package network.something.somepotter.cast.projectile_or_self;

import network.something.somepotter.cast.projectile.ProjectileCast;
import network.something.somepotter.cast.self.SelfCast;
import network.something.somepotter.event.SpellCastEvent;

public class ProjectileOrSelfCast {

    public SpellCastEvent<?> event;
    protected int range = 32;
    protected boolean canHitFluid = false;
    protected float velocity = 1.5F;


    public ProjectileOrSelfCast(SpellCastEvent<?> event) {
        this.event = event;
    }

    public void execute() {
        if (event.caster.isCrouching()) {
            var cast = new SelfCast(event);
            cast.execute();
            return;
        }

        var cast = new ProjectileCast(event);
        cast.setRange(range);
        cast.canHitFluid(canHitFluid);
        cast.setVelocity(velocity);
        cast.execute();
    }
}
