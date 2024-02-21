package network.something.somepotter.spells.spell.tempest;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spells.spell.SpellListener;

public class TempestListener extends SpellListener<TempestSpell> {

    public static void strike(ServerLevel level, Vec3 pos) {
        var bolt = EntityType.LIGHTNING_BOLT.create(level);
        if (bolt == null) return;

        bolt.setPos(pos);
        level.addFreshEntity(bolt);
    }

    @Override
    public void onSpellHitBlock(SpellHitEvent.Post<TempestSpell> event, BlockHitResult hitResult) {
        strike(event.level, Vec3.atCenterOf(hitResult.getBlockPos()));
    }

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<TempestSpell> event, EntityHitResult hitResult, Entity entity) {
        strike(event.level, entity.getEyePosition());
    }
}
