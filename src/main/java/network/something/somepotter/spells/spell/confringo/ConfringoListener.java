package network.something.somepotter.spells.spell.confringo;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.spells.spell.SpellListener;

public class ConfringoListener extends SpellListener<ConfringoSpell> {

    public static void explode(ServerLevel level, Vec3 pos, LivingEntity caster, float areaOfEffect) {
        var fireArea = new AABB(pos, pos).inflate(areaOfEffect);
        level.getEntities(null, fireArea).forEach(entity -> {
            entity.setRemainingFireTicks(20 * 10);
        });

        level.explode(
                caster,
                SpellInit.get(ConfringoSpell.ID).getDamageSource(caster),
                null,
                pos.x, pos.y, pos.z,
                areaOfEffect,
                true,
                Explosion.BlockInteraction.NONE
        );
    }

    @Override
    public void onSpellHitBlock(SpellHitEvent.Post<ConfringoSpell> event, BlockHitResult hitResult) {
        explode(event.level, hitResult.getLocation(), event.caster, event.areaOfEffect);
    }

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<ConfringoSpell> event, EntityHitResult hitResult, Entity entity) {
        explode(event.level, hitResult.getEntity().getEyePosition(), event.caster, event.areaOfEffect);
    }
}
