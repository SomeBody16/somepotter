package network.something.somepotter.spell.spells.melofors;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;

public class MeloforsHitListener extends SpellHitListener {
    public MeloforsHitListener() {
        super(MeloforsSpell.ID);
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            var helmet = livingEntity.getItemBySlot(EquipmentSlot.HEAD);
            var pumpkinStack = Items.CARVED_PUMPKIN.getDefaultInstance();

            if (helmet.isEmpty()) {
                pumpkinStack.enchant(Enchantments.BINDING_CURSE, 1);
            } else {
                livingEntity.spawnAtLocation(helmet);
            }

            livingEntity.setItemSlot(EquipmentSlot.HEAD, pumpkinStack);
        }
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
    }
}
