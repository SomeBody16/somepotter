package network.something.somepotter.spells.spell.aguamenti;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.tickable.Tickables;

public class AguamentiListener extends SpellListener<AguamentiSpell> {

    public static void playEmptySound(ServerLevel level, BlockPos pos) {
        var soundEvent = SoundEvents.BUCKET_EMPTY;
        level.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<AguamentiSpell> event, EntityHitResult hitResult, Entity entity) {
        if (entity.isOnFire()) {
            entity.clearFire();
        }
    }

    @Override
    public void onSpellHitBlock(SpellHitEvent.Post<AguamentiSpell> event, BlockHitResult hitResult) {
        var blockPos = hitResult.getBlockPos();
        onSpellHitBlock(event.level, blockPos, hitResult, event.areaOfEffect, true);
    }

    public void onSpellHitBlock(ServerLevel level, BlockPos blockPos, BlockHitResult hitResult, float areaOfEffect, boolean tryRelative) {
        var blockState = level.getBlockState(blockPos);

        // Extinguish fire
        if (blockState.getBlock() instanceof BaseFireBlock
                && blockState.canBeReplaced(Fluids.WATER)) {
            extinguish(level, blockPos, areaOfEffect);
            return;
        }

        // If block can be waterlogged
        if (blockState.getBlock() instanceof LiquidBlockContainer liquidBlockContainer
                && liquidBlockContainer.canPlaceLiquid(level, blockPos, blockState, Fluids.WATER)) {
            liquidBlockContainer.placeLiquid(level, blockPos, blockState, Fluids.WATER.getSource(true));
            playEmptySound(level, blockPos);
            return;
        }

        // Can place water
        if (blockState.is(Blocks.AIR)) {
            level.setBlockAndUpdate(blockPos, Blocks.WATER.defaultBlockState());
            playEmptySound(level, blockPos);
            return;
        }

        if (tryRelative) {
            // Relative to target block
            blockPos = blockPos.relative(hitResult.getDirection());
            onSpellHitBlock(level, blockPos, hitResult, areaOfEffect, false);
        }
    }

    protected void extinguish(ServerLevel level, BlockPos blockPos, float areaOfEffect) {
        var tickable = new ExtinguishFireTickable(level, blockPos, areaOfEffect);
        Tickables.add(tickable);
    }
}
