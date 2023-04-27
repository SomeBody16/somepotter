package network.something.somepotter.spell.spells.basic_cast;

import net.minecraft.server.level.ServerLevel;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;

public class BasicCastHitListener extends SpellHitListener {
    public BasicCastHitListener() {
        super(BasicCastSpell.ID);
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
        event.getEntity().hurt(
                BasicCastSpell.getDamageSource(event.getCaster()),
                BasicCastSpell.DAMAGE
        );
        event.getCaster().setLastHurtMob(event.getEntity());
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
    }
}
