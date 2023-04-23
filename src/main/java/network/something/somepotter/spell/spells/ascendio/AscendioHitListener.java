package network.something.somepotter.spell.spells.ascendio;

import net.minecraft.server.level.ServerLevel;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;

public class AscendioHitListener extends SpellHitListener {
    public AscendioHitListener() {
        super(AscendioSpell.ID);
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
        var target = event.getEntity();
        var motion = target.getDeltaMovement().add(0, AscendioSpell.FORCE, 0);
        target.setDeltaMovement(motion);
        target.fallDistance = 0;
        target.hurtMarked = true;
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
    }
}
