package network.something.somepotter.spells.spell.incendio;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.util.AbilityPowerUtil;

public class IncendioListener extends SpellListener<IncendioSpell> {

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<IncendioSpell> event, EntityHitResult hitResult, Entity entity) {
        var duration = AbilityPowerUtil.scale(event.abilityPower, 20 * 5, 20 * 60);
        entity.setRemainingFireTicks(duration);
    }

    @Override
    public void onSpellHitBlock(SpellHitEvent.Post<IncendioSpell> event, BlockHitResult hitResult) {
        var blockPos = hitResult.getBlockPos();
        var blockState = event.level.getBlockState(blockPos);
        var direction = hitResult.getDirection();

        if (blockState.getBlock() instanceof TntBlock) {
            blockState.getBlock().onCaughtFire(blockState, event.level, blockPos, direction, event.caster);
            event.level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 11);

        } else if (!CampfireBlock.canLight(blockState)
                && !CandleBlock.canLight(blockState)
                && !CandleCakeBlock.canLight(blockState)
        ) {
            var targetPos = blockPos.relative(direction);
            if (BaseFireBlock.canBePlacedAt(event.level, targetPos, event.caster.getDirection())) {
                playFireSound(event.level, targetPos);
                var fireBlockState = BaseFireBlock.getState(event.level, targetPos);
                event.level.setBlock(targetPos, fireBlockState, 11);
                event.level.gameEvent(event.caster, GameEvent.BLOCK_PLACE, blockPos);
            }
        } else {
            playFireSound(event.level, blockPos);
            event.level.setBlock(blockPos, blockState.setValue(BlockStateProperties.LIT, Boolean.TRUE), 11);
            event.level.gameEvent(event.caster, GameEvent.BLOCK_PLACE, blockPos);
        }

        // Try to remove water
        if (!blockState.is(Blocks.WATER)) {
            blockPos = blockPos.relative(direction);
            blockState = event.level.getBlockState(blockPos);
        }

        if (blockState.is(Blocks.WATER)) {
            event.level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
            playFireSound(event.level, blockPos);
        }
    }

    protected void playFireSound(LevelAccessor pLevel, BlockPos pPos) {
        var soundevent = SoundEvents.FLINTANDSTEEL_USE;
        pLevel.playSound(null, pPos, soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
    }
}
