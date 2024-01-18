package network.something.somepotter.spells.spell.reducio;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.util.AbilityPowerUtil;
import virtuoel.pehkui.api.ScaleTypes;

import static net.minecraft.Util.NIL_UUID;

public class ReducioListener extends SpellListener<ReducioSpell> {


    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<ReducioSpell> event, EntityHitResult hitResult, Entity entity) {
        var scaleData = ScaleTypes.BASE.getScaleData(entity);

        var subtraction = AbilityPowerUtil.scale(event.abilityPower, 0.1F, 0.5F);
        var newScale = scaleData.getScale() - subtraction;

        if (scaleData.getScale() > 1 && newScale < 1) {
            newScale = 1;
        }

        newScale = Math.max(newScale, 0.02F);
        if (event.caster instanceof ServerPlayer player) {
            var text = String.format("%.2f -> %.2f", scaleData.getScale(), newScale);
            var message = new TextComponent(text).withStyle(ChatFormatting.GREEN);
            player.sendMessage(message, ChatType.GAME_INFO, NIL_UUID);
        }

        scaleData.setTargetScale(newScale);
    }

}
