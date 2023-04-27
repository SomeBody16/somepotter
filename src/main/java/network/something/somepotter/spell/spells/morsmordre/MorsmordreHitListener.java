package network.something.somepotter.spell.spells.morsmordre;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import network.something.somepotter.init.EntityInit;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;

public class MorsmordreHitListener extends SpellHitListener {
    public MorsmordreHitListener() {
        super(MorsmordreSpell.ID);
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
        // Spawn dark mark
        EntityInit.DARK_MARK.get().spawn(
                level, null, null,
                event.getBlockPos(), MobSpawnType.MOB_SUMMONED,
                false, false
        );
    }
}
