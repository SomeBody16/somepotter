package network.something.somepotter.spell.spells.colloportus;

import net.minecraft.server.level.ServerLevel;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;
import network.something.somepotter.util.DoorUtil;

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
        DoorUtil.setOpen(level, blockState, event.getBlockPos(), false);
    }
}
