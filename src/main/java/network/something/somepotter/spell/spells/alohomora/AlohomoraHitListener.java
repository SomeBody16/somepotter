package network.something.somepotter.spell.spells.alohomora;

import net.minecraft.server.level.ServerLevel;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;
import network.something.somepotter.util.DoorUtil;

public class AlohomoraHitListener extends SpellHitListener {
    public AlohomoraHitListener() {
        super(AlohomoraSpell.ID);
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
        var blockState = level.getBlockState(event.getBlockPos());
        DoorUtil.setOpen(level, blockState, event.getBlockPos(), true);
    }
}
