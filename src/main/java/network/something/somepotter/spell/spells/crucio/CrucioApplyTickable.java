package network.something.somepotter.spell.spells.crucio;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.item.ItemWand;
import network.something.somepotter.spell.effect.SpellEffects;
import network.something.somepotter.spell.tickable.core.EntitySpellTickable;

public class CrucioApplyTickable extends EntitySpellTickable {
    public CrucioApplyTickable(LivingEntity caster, int duration, Entity target) {
        super(caster, duration, target);
    }

    @Override
    public void tick() {
        if (target instanceof LivingEntity livingEntity) {
            var crucioEffect = new MobEffectInstance(SpellEffects.CRUCIO.get(),
                    CrucioSpell.REFRESH_DURATION, 10);
            livingEntity.addEffect(crucioEffect);
        }
    }

    @Override
    public boolean isExpired() {
        return !isLookingAtTarget()
                || !caster.isUsingItem()
                || !(caster.getUseItem().getItem() instanceof ItemWand);
    }

    private boolean isLookingAtTarget() {
        if (caster == null) {
            return false;
        }

        Vec3 vec3 = caster.getViewVector(1.0F).normalize();
        Vec3 vec31 = new Vec3(target.getX() - caster.getX(), target.getEyeY() - caster.getEyeY(), target.getZ() - caster.getZ());
        double d0 = vec31.length();
        vec31 = vec31.normalize();
        double d1 = vec3.dot(vec31);
        return d1 > (1.0D - CrucioSpell.LOOK_THRESHOLD / d0) && caster.hasLineOfSight(target);
    }
}
