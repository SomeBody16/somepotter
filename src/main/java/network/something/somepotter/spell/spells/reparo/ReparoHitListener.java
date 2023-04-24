package network.something.somepotter.spell.spells.reparo;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.AABB;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;
import network.something.somepotter.spell.tickable.SpellTickables;

public class ReparoHitListener extends SpellHitListener {
    public ReparoHitListener() {
        super(ReparoSpell.ID);
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
        var areaOfEffect = new AABB(event.getBlockPos()).inflate(ReparoSpell.RANGE);
        var tickable = new ReparoTickable(event.getCaster(), areaOfEffect);
        SpellTickables.addTickable(tickable);
    }
}
