package network.something.somepotter.spell.cast.projectile_or_self;

import net.minecraft.world.entity.LivingEntity;
import network.something.somepotter.spell.cast.AbstractCast;
import network.something.somepotter.spell.cast.projectile.ProjectileCast;
import network.something.somepotter.spell.cast.self.SelfCast;

public class ProjectileOrSelfCast extends AbstractCast {

    protected int range = 64;
    protected float speed = 3.0f;
    protected boolean canHitFluid = false;

    @Override
    public String getId() {
        return "projectile_or_self";
    }

    @Override
    public void execute(LivingEntity caster, String spellId) {
        if (caster.isCrouching()) {
            var cast = new SelfCast();
            cast.execute(caster, spellId);
            return;
        }

        var cast = new ProjectileCast();
        cast.setRange(range);
        cast.setSpeed(speed);
        cast.setCanHitFluid(canHitFluid);
        cast.execute(caster, spellId);
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setCanHitFluid(boolean canHitFluid) {
        this.canHitFluid = canHitFluid;
    }
}
