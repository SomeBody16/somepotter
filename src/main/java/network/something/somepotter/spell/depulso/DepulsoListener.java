package network.something.somepotter.spell.depulso;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.util.AbilityPowerUtil;

public class DepulsoListener extends SpellListener<DepulsoSpell> {

    public static void push(LivingEntity caster, Entity target, float strength) {
        var xRatio = Math.sin(caster.getYRot() * ((float) Math.PI / 180F));
        var zRatio = -Math.cos(caster.getYRot() * ((float) Math.PI / 180F));

        var vec3 = target.getDeltaMovement();
        var vec31 = (new Vec3(xRatio, 0.0D, zRatio)).normalize().scale(strength);

        target.setDeltaMovement(
                vec3.x / 2.0D - vec31.x,
                target.isOnGround() ? Math.min(0.4D, vec3.y / 2.0D + strength) : vec3.y,
                vec3.z / 2.0D - vec31.z
        );
        target.hasImpulse = true;
        target.hurtMarked = true;
        target.fallDistance = 0;
    }

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<DepulsoSpell> event, EntityHitResult hitResult, Entity entity) {
        var strength = AbilityPowerUtil.scale(event.abilityPower, 2.0F, 4.0F);
        push(event.caster, entity, strength);
    }
}
