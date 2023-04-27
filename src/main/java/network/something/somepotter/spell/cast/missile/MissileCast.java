package network.something.somepotter.spell.cast.missile;

import net.minecraft.world.entity.LivingEntity;
import network.something.somepotter.init.EntityInit;
import network.something.somepotter.spell.api.util.ProjectileUtil;
import network.something.somepotter.spell.cast.AbstractCast;

public class MissileCast extends AbstractCast {

    protected int range = 64;
    protected float speed = 3.0f;
    protected int lifeTime = 20 * 3;
    protected boolean canHitFluid = false;

    @Override
    public String getId() {
        return "missile";
    }

    @Override
    public void execute(LivingEntity caster, String spellId) {
        var missile = new SpellMissileEntity(EntityInit.SPELL_MISSILE.get(),
                caster.level, caster, spellId, lifeTime);
        ProjectileUtil.castProjectile(caster, missile, range, canHitFluid, speed);
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    public void setCanHitFluid(boolean canHitFluid) {
        this.canHitFluid = canHitFluid;
    }
}
