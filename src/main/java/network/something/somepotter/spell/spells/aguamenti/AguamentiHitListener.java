package network.something.somepotter.spell.spells.aguamenti;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.material.Fluids;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;

public class AguamentiHitListener extends SpellHitListener {
    public AguamentiHitListener() {
        super(AguamentiSpell.ID);
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
        if (event.getEntity().isOnFire()) {
            event.getEntity().clearFire();
        }
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
        var blockPos = event.getBlockPos();
        var blockState = level.getBlockState(blockPos);

        // Extinguish fire
        if (blockState.getBlock() instanceof BaseFireBlock fireBlock
                && blockState.canBeReplaced(Fluids.WATER)) {
            level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
            playEmptySound(level, blockPos);
            return;
        }

        // If block can be waterlogged
        if (blockState.getBlock() instanceof LiquidBlockContainer liquidBlockContainer
                && liquidBlockContainer.canPlaceLiquid(level, blockPos, blockState, Fluids.WATER)) {
            liquidBlockContainer.placeLiquid(level, blockPos, blockState, Fluids.WATER.getSource(true));
            playEmptySound(level, blockPos);
            return;
        }

        // Relative to target block
        blockPos = blockPos.relative(event.getDirection());
        blockState = level.getBlockState(blockPos);

        if (blockState.canBeReplaced(Fluids.WATER)) {
            level.setBlockAndUpdate(blockPos, Blocks.WATER.defaultBlockState());
            playEmptySound(level, blockPos);
        }
    }

    protected void playEmptySound(LevelAccessor pLevel, BlockPos pPos) {
        SoundEvent soundevent = SoundEvents.BUCKET_EMPTY;
        pLevel.playSound(null, pPos, soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
    }
}
