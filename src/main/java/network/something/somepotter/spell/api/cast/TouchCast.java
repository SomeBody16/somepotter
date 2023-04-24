package network.something.somepotter.spell.api.cast;

import net.minecraft.world.entity.LivingEntity;
import network.something.somepotter.entity.SpellTouchEntity;
import network.something.somepotter.init.EntityInit;
import network.something.somepotter.spell.api.util.ProjectileUtil;

public class TouchCast extends AbstractCast {

    protected int range = 16;
    protected boolean canHitFluid = false;

    @Override
    public void execute(LivingEntity caster, String spellId) {
        var spellProjectile = new SpellTouchEntity(EntityInit.SPELL_TOUCH.get(), caster.level, caster, spellId);
        ProjectileUtil.castProjectile(caster, spellProjectile, range, canHitFluid, 100);
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setCanHitFluid(boolean canHitFluid) {
        this.canHitFluid = canHitFluid;
    }
}
