package network.something.somepotter.spell.accio;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.cast.touch.TouchCast;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.tickable.Tickables;
import network.something.somepotter.util.AbilityPowerUtil;

import java.util.stream.Stream;

public class AccioListener extends SpellListener<AccioSpell> {

    @Override
    public void onSpellCast(SpellCastEvent.Post<AccioSpell> event) {
        new TouchCast(event).execute();
    }

    @Override
    public void onSpellHitBlock(SpellHitEvent.Post<AccioSpell> event, BlockHitResult hitResult) {
        var area = new AABB(hitResult.getBlockPos()).deflate(1).inflate(event.areaOfEffect * 1.5);

        var entities = Stream.concat(
                event.level.getEntities(EntityType.ITEM, area, entity -> true).stream(),
                event.level.getEntities(EntityType.EXPERIENCE_ORB, area, entity -> true).stream()
        ).toList();

        var config = AccioSpell.CONFIG.get();
        var duration = AbilityPowerUtil.scale(event.abilityPower, config.durationMin, config.durationMax);
        var speed = AbilityPowerUtil.scale(event.abilityPower, config.speedMin, config.speedMax);

        var tickable = new AccioTickable(duration, event.caster, entities, speed);
        Tickables.add(tickable);
    }

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<AccioSpell> event, EntityHitResult hitResult, Entity entity) {
        var blockHitResult = new BlockHitResult(
                hitResult.getLocation(),
                Direction.DOWN,
                new BlockPos(hitResult.getLocation()),
                true
        );
        onSpellHitBlock(event, blockHitResult);
    }

    @Override
    public void onSpellHitPlayer(SpellHitEvent.Post<AccioSpell> event, EntityHitResult hitResult, ServerPlayer player) {
        onSpellHitEntity(event, hitResult, player);
    }
}
