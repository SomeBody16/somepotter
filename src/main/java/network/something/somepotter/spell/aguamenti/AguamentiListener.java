package network.something.somepotter.spell.aguamenti;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spell.SpellListener;

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
        var blockState = event.level.getBlockState(blockPos);

        // TODO: Extinguish fire
//        if (blockState.getBlock() instanceof BaseFireBlock) {
//            var tickable = new ExtinguishFireTickable(event.level, blockPos, event.abilityPower, (int) event.areaOfEffect);
//            Tickables.add(tickable);
//            return;
//        }

        // If block can be waterlogged
        if (blockState.getBlock() instanceof LiquidBlockContainer liquidBlockContainer
                && liquidBlockContainer.canPlaceLiquid(event.level, blockPos, blockState, Fluids.WATER)) {
            liquidBlockContainer.placeLiquid(event.level, blockPos, blockState, Fluids.WATER.getSource(true));
            playEmptySound(event.level, blockPos);
            return;
        }

        // Relative to target block
        blockPos = blockPos.relative(hitResult.getDirection());
        blockState = event.level.getBlockState(blockPos);

        if (blockState.canBeReplaced(Fluids.WATER)) {
            event.level.setBlockAndUpdate(blockPos, Blocks.WATER.defaultBlockState());
            playEmptySound(event.level, blockPos);
        }
    }
}
