package network.something.somepotter.spells.spell.protego_maxima;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.effect.Effects;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell.protego_maxima.claim.ClaimManager;

import static net.minecraft.Util.NIL_UUID;

public class ProtegoMaximaListener extends SpellListener<ProtegoMaximaSpell> {

    @Override
    public void onSpellHitBlock(SpellHitEvent.Post<ProtegoMaximaSpell> event, BlockHitResult hitResult) {
        if (event.caster instanceof ServerPlayer caster) {
            Component message;
            var pos = new ChunkPos(hitResult.getBlockPos());

            if (caster.isCrouching()) { // Remove claim
                if (!ClaimManager.exists(event.level, pos)) {
                    message = new TranslatableComponent("spell.protego_maxima.claim.not_exists")
                            .withStyle(ChatFormatting.RED);
                } else if (!ClaimManager.hasAccess(event.level, caster, pos)) {
                    message = new TranslatableComponent("spell.protego_maxima.claim.access_denied")
                            .withStyle(ChatFormatting.RED);
                } else {
                    ClaimManager.remove(event.level, pos);
                    message = new TranslatableComponent("spell.protego_maxima.claim.removed")
                            .withStyle(ChatFormatting.GREEN);
                }

            } else { // Add claim

                if (ClaimManager.exists(event.level, pos)) {
                    message = new TranslatableComponent("spell.protego_maxima.claim.already_exists")
                            .withStyle(ChatFormatting.RED);
                } else {
                    ClaimManager.add(event.level, pos, caster);
                    message = new TranslatableComponent("spell.protego_maxima.claim.added")
                            .withStyle(ChatFormatting.GREEN);
                }

            }

            var color = SpellInit.get(ProtegoMaximaSpell.ID).getColor();
            Effects.chunkHighlight(event.level, pos, hitResult.getBlockPos().getY(), color);

            caster.sendMessage(message, ChatType.GAME_INFO, NIL_UUID);
        }
    }

    @Override
    public void onSpellHitPlayer(SpellHitEvent.Post<ProtegoMaximaSpell> event, EntityHitResult hitResult, ServerPlayer target) {
        if (event.caster instanceof ServerPlayer caster) {
            Component message;

            if (event.caster.isCrouching()) { // Remove friend

                ClaimManager.removeFriend(event.level, caster, target);
                message = new TranslatableComponent("spell.protego_maxima.friend.removed")
                        .withStyle(ChatFormatting.GREEN);

            } else { // Add friend

                ClaimManager.addFriend(event.level, caster, target);
                message = new TranslatableComponent("spell.protego_maxima.friend.added")
                        .withStyle(ChatFormatting.GREEN);

            }

            caster.sendMessage(message, ChatType.GAME_INFO, NIL_UUID);
            target.sendMessage(message, ChatType.GAME_INFO, NIL_UUID);
        }
    }
}
