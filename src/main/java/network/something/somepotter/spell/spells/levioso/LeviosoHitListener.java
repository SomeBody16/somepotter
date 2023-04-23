package network.something.somepotter.spell.spells.levioso;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;
import network.something.somepotter.spell.tickable.SpellTickables;

public class LeviosoHitListener extends SpellHitListener {
    public LeviosoHitListener() {
        super(LeviosoSpell.ID);
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
        if (event.getEntity() instanceof LivingEntity hitEntity) {
            var leviosoTickable = new LeviosoTickable(event.getCaster(), hitEntity);
            SpellTickables.addTickable(leviosoTickable);
        }
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
    }
}
