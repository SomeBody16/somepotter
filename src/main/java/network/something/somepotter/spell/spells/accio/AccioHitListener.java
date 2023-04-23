package network.something.somepotter.spell.spells.accio;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.AABB;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;

public class AccioHitListener extends SpellHitListener {
    public AccioHitListener() {
        super(AccioSpell.ID);
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
        if (event.getEntity() instanceof ItemEntity itemEntity) {
            AccioPullEffect.add(itemEntity, event.getCaster(), AccioSpell.PULL_DURATION);
        }
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
        var blockPos = event.getBlockPos();
        AABB areaOfEffect = new AABB(blockPos).inflate(AccioSpell.BLOCK_HIT_RANGE);

        level.getEntities(null, areaOfEffect).forEach(entity -> {
            if (entity instanceof ItemEntity itemEntity) {
                AccioPullEffect.add(itemEntity, event.getCaster(), AccioSpell.PULL_DURATION);
            }
        });
    }
}
