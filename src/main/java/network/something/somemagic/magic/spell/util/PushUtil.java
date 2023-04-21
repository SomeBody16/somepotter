package network.something.somemagic.magic.spell.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class PushUtil {

    public static void fromCaster(LivingEntity caster, Entity target) {
        target.hasImpulse = true;

        var xRatio = Math.sin(caster.getYRot() * ((float) Math.PI / 180F));
        var zRatio = -Math.cos(caster.getYRot() * ((float) Math.PI / 180F));
        var strength = 2.0F;

        var vec3 = target.getDeltaMovement();
        var vec31 = (new Vec3(xRatio, 0.0D, zRatio)).normalize().scale(strength);

        target.setDeltaMovement(
                vec3.x / 2.0D - vec31.x,
                target.isOnGround() ? Math.min(0.4D, vec3.y / 2.0D + strength) : vec3.y,
                vec3.z / 2.0D - vec31.z
        );
    }

}