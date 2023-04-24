package network.something.somepotter.spell.spells.levioso;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.item.ItemWand;
import network.something.somepotter.spell.tickable.core.EntitySpellTickable;

public class LeviosoTickable extends EntitySpellTickable {

    public final double distance;

    public LeviosoTickable(LivingEntity caster, LivingEntity target) {
        super(caster, 0, target);
        this.distance = caster.getEyePosition()
                .distanceTo(target.getEyePosition());

        target.setNoGravity(true);
    }

    @Override
    public void tick() {
        Vec3 originPos = caster.getEyePosition();
        Vec3 lookAngle = caster.getLookAngle();

        var targetPos = originPos.add(lookAngle.scale(distance));

        var motion = targetPos.subtract(target.getEyePosition())
                .normalize().scale(LeviosoSpell.PULL_STRENGTH);
        target.setDeltaMovement(motion);
        target.hurtMarked = true;
        target.fallDistance = 0;
        target.hasImpulse = true;
    }

    @Override
    public void onExpired() {
        target.setNoGravity(false);
    }

    @Override
    public boolean isExpired() {
        var distance = caster.getEyePosition().distanceTo(target.getEyePosition());
        return !caster.isUsingItem()
                || !(caster.getUseItem().getItem() instanceof ItemWand)
                || distance > LeviosoSpell.RANGE
                || !caster.hasLineOfSight(target);
    }
}
