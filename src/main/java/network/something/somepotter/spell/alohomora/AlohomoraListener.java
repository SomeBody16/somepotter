package network.something.somepotter.spell.alohomora;

import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.phys.BlockHitResult;
import network.something.somepotter.cast.touch.TouchCast;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spell.SpellListener;

public class AlohomoraListener extends SpellListener<AlohomoraSpell> {

    @Override
    public void onSpellCast(SpellCastEvent.Post<AlohomoraSpell> event) {
        new TouchCast(event)
                .setRange(3)
                .execute();
    }

    @Override
    public void onSpellHitBlock(SpellHitEvent.Post<AlohomoraSpell> event, BlockHitResult hitResult) {
        var blockPos = hitResult.getBlockPos();
        var blockState = event.level.getBlockState(blockPos);

        if (blockState.getBlock() instanceof DoorBlock doorBlock) {
            doorBlock.setOpen(null, event.level, blockState, blockPos, true);
        }
        if (blockState.getBlock() instanceof TrapDoorBlock) {
            blockState.setValue(TrapDoorBlock.OPEN, true);
            event.level.setBlockAndUpdate(blockPos, blockState);
        }
    }
}
