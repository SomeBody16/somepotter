package network.something.somepotter.spell.spells.expelliarmus;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;
import network.something.somepotter.spell.spells.accio.AccioSpellTickable;
import network.something.somepotter.spell.tickable.SpellTickables;
import network.something.somepotter.util.PushUtil;

public class ExpelliarmusHitListener extends SpellHitListener {
    public ExpelliarmusHitListener() {
        super(ExpelliarmusSpell.ID);
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity
                && !livingEntity.getMainHandItem().isEmpty()
        ) {
            var item = livingEntity.spawnAtLocation(livingEntity.getMainHandItem());
            livingEntity.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);

            var accioTickable = new AccioSpellTickable(event.getCaster(),
                    ExpelliarmusSpell.ACCIO_DURATION, item);
            SpellTickables.addTickable(accioTickable);
            return;
        }

        PushUtil.fromCaster(event.getCaster(), event.getEntity(), ExpelliarmusSpell.PUSH_FORCE);
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
    }
}
