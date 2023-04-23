package network.something.somepotter.spell.spells.depulso;

import net.minecraft.server.level.ServerLevel;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;
import network.something.somepotter.spell.util.PushUtil;

public class DepulsoHitListener extends SpellHitListener {
    public DepulsoHitListener() {
        super(DepulsoSpell.ID);
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
        PushUtil.fromCaster(event.getCaster(), event.getEntity(), DepulsoSpell.STRENGTH);
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
    }
}
