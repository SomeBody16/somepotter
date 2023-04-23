package network.something.somepotter.spell.api.cast;

import net.minecraft.world.entity.LivingEntity;
import network.something.somepotter.entity.SpellProjectileEntity;
import network.something.somepotter.init.EntityInit;
import network.something.somepotter.spell.api.util.ProjectileUtil;

public class ProjectileCast extends AbstractCast {

    protected int range = 64;
    protected float speed = 3.0f;
    protected boolean canHitFluid = false;

    @Override
    public void execute(LivingEntity caster, String spellId) {
        var spellProjectile = new SpellProjectileEntity(EntityInit.SPELL_PROJECTILE.get(), caster.level, caster, spellId);
        ProjectileUtil.castProjectile(caster, spellProjectile, range, canHitFluid, speed);
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
