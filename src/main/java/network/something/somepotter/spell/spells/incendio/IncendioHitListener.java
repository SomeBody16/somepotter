package network.something.somepotter.spell.spells.incendio;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;

public class IncendioHitListener extends SpellHitListener {
    public IncendioHitListener() {
        super(IncendioSpell.ID);
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
        event.getEntity().setRemainingFireTicks(IncendioSpell.FIRE_DURATION);
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
        var blockPos = event.getBlockPos();
        var blockState = level.getBlockState(blockPos);
        var direction = event.getDirection();

        if (blockState.getBlock() instanceof TntBlock) {
            blockState.getBlock().onCaughtFire(blockState, level, blockPos, direction, event.getCaster());
            level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 11);

        } else if (!CampfireBlock.canLight(blockState)
                && !CandleBlock.canLight(blockState)
                && !CandleCakeBlock.canLight(blockState)
        ) {
            var targetPos = blockPos.relative(direction);
            if (BaseFireBlock.canBePlacedAt(level, targetPos, event.getCaster().getDirection())) {
                playFireSound(level, targetPos);
                var fireBlockState = BaseFireBlock.getState(level, targetPos);
                level.setBlock(targetPos, fireBlockState, 11);
                level.gameEvent(event.getCaster(), GameEvent.BLOCK_PLACE, blockPos);
            }
        } else {
            playFireSound(level, blockPos);
            level.setBlock(blockPos, blockState.setValue(BlockStateProperties.LIT, Boolean.TRUE), 11);
            level.gameEvent(event.getCaster(), GameEvent.BLOCK_PLACE, blockPos);
        }
    }

    protected void playFireSound(LevelAccessor pLevel, BlockPos pPos) {
        SoundEvent soundevent = SoundEvents.FLINTANDSTEEL_USE;
        pLevel.playSound(null, pPos, soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
    }
}
