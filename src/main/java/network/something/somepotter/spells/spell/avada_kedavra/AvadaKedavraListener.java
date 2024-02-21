package network.something.somepotter.spells.spell.avada_kedavra;

import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spells.spell.SpellListener;

public class AvadaKedavraListener extends SpellListener<AvadaKedavraSpell> {

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<AvadaKedavraSpell> event, EntityHitResult hitResult, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            var damageSource = event.spell.getDamageSource(event.caster);
            livingEntity.hurt(damageSource, Float.MAX_VALUE);

            if (livingEntity instanceof ServerPlayer player) {
                var message = new TranslatableComponent("death.attack.avada_kedavra",
                        player.getDisplayName(), event.caster.getDisplayName());
                player.server.getPlayerList().broadcastMessage(message, ChatType.CHAT, event.caster.getUUID());
            }
        }
    }
}
