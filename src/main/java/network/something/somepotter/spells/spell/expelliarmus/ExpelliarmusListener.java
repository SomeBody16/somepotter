package network.something.somepotter.spells.spell.expelliarmus;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell.depulso.DepulsoListener;
import network.something.somepotter.util.AbilityPowerUtil;
import network.something.somepotter.util.WandUtil;

import javax.annotation.Nullable;

public class ExpelliarmusListener extends SpellListener<ExpelliarmusSpell> {

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<ExpelliarmusSpell> event, EntityHitResult hitResult, Entity entity) {

        if (entity instanceof LivingEntity livingEntity) {
            var handToDrop = getHandToDrop(livingEntity);
            if (handToDrop != null) {
                var itemToDrop = livingEntity.getItemInHand(handToDrop);
                livingEntity.setItemInHand(handToDrop, ItemStack.EMPTY);
                livingEntity.spawnAtLocation(itemToDrop);
            }
        }

        var strength = AbilityPowerUtil.scale(event.abilityPower, 2.0F, 3.0F);
        DepulsoListener.push(event.caster, entity, strength);
    }

    @Nullable
    protected InteractionHand getHandToDrop(LivingEntity entity) {
        var mainHandItem = entity.getMainHandItem();
        var offHandItem = entity.getOffhandItem();

        if (WandUtil.isItemWand(mainHandItem)) {
            return InteractionHand.MAIN_HAND;
        }
        if (WandUtil.isItemWand(offHandItem)) {
            return InteractionHand.OFF_HAND;
        }

        if (!mainHandItem.isEmpty()) {
            return InteractionHand.MAIN_HAND;
        }
        if (!offHandItem.isEmpty()) {
            return InteractionHand.OFF_HAND;
        }

        return null;
    }
}
