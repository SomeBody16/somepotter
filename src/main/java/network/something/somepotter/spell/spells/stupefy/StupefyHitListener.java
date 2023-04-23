package network.something.somepotter.spell.spells.stupefy;

import net.minecraft.server.level.ServerLevel;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;
import network.something.somepotter.util.PushUtil;

public class StupefyHitListener extends SpellHitListener {
    public StupefyHitListener() {
        super(StupefySpell.ID);
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
        var hitEntity = event.getEntity();

        float randomXRot = hitEntity.level.random.nextFloat() * 360.0F;
        float randomYRot = hitEntity.level.random.nextFloat() * 360.0F;

        hitEntity.setXRot(randomXRot);
        hitEntity.setYRot(randomYRot);

        PushUtil.fromCaster(event.getCaster(), hitEntity, StupefySpell.STRENGTH);
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
    }
}
