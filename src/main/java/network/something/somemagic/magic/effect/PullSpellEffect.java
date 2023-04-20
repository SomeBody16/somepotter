package network.something.somemagic.magic.effect;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class PullSpellEffect extends SpellEffect {
    public PullSpellEffect(LivingEntity caster, Entity target) {
        super(caster, target, 20 * 5);
    }

    @Override
    public void tick() {
        var casterPos = caster.getEyePosition();
        var targetPos = target.getEyePosition();

        var motion = casterPos.subtract(targetPos);
        var distance = motion.length();
        var speed = 2.0D;

        if (distance <= 0.5) {
            this.duration = 0;
            return;
        }

        motion = motion.multiply(
                new Vec3(
                        speed / distance,
                        speed / distance,
                        speed / distance
                )
        );
        target.setDeltaMovement(motion);
    }
}
