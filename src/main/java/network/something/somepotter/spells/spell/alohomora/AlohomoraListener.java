package network.something.somepotter.spells.spell.alohomora;

import iskallia.vault.init.ModBlocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.phys.BlockHitResult;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.integration.Integrations;
import network.something.somepotter.spells.spell.SpellListener;

public class AlohomoraListener extends SpellListener<AlohomoraSpell> {

    @Override
    public void onSpellHitBlock(SpellHitEvent.Post<AlohomoraSpell> event, BlockHitResult hitResult) {
        var blockPos = hitResult.getBlockPos();
        var blockState = event.level.getBlockState(blockPos);

        if (Integrations.THE_VAULT.isLoaded()) {
            if (blockState.getBlock().getRegistryName()
                    .equals(ModBlocks.TREASURE_DOOR.getRegistryName())) {
                return;
            }
        }

        if (blockState.getBlock() instanceof DoorBlock doorBlock) {
            doorBlock.setOpen(null, event.level, blockState, blockPos, true);
        }
        if (blockState.getBlock() instanceof TrapDoorBlock) {
            blockState.setValue(TrapDoorBlock.OPEN, true);
            event.level.setBlockAndUpdate(blockPos, blockState);
        }
    }
}
