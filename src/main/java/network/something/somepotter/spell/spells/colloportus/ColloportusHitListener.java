package network.something.somepotter.spell.spells.colloportus;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.DoorBlock;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;

public class ColloportusHitListener extends SpellHitListener {
    public ColloportusHitListener() {
        super(ColloportusSpell.ID);
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
        var blockState = level.getBlockState(event.getBlockPos());

        if (blockState.getBlock() instanceof DoorBlock doorBlock) {
            doorBlock.setOpen(event.getCaster(), level, blockState, event.getBlockPos(), false);
        }
    }
}
