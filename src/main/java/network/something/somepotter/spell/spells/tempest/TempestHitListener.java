package network.something.somepotter.spell.spells.tempest;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;

public class TempestHitListener extends SpellHitListener {
    public TempestHitListener() {
        super(TempestSpell.ID);
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
        boltTarget(level, event.getLocation());
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
        boltTarget(level, event.getLocation());
    }

    public void boltTarget(ServerLevel level, Vec3 pos) {
        var bolt = EntityType.LIGHTNING_BOLT.create(level);
        if (bolt == null) {
            return;
        }

        bolt.setPos(pos);
        level.addFreshEntity(bolt);
    }
}
