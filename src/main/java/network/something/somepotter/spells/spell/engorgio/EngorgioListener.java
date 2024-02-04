package network.something.somepotter.spells.spell.engorgio;

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

public class EngorgioListener extends SpellListener<EngorgioSpell> {

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<EngorgioSpell> event, EntityHitResult hitResult, Entity entity) {
        var scaleData = ScaleTypes.BASE.getScaleData(entity);

        var addition = AbilityPowerUtil.scale(event.abilityPower, 0.1F, 0.5F);
        var newScale = scaleData.getScale() + addition;

        if (scaleData.getScale() < 1 && newScale > 1) {
            newScale = 1;
        }

        newScale = Math.min(newScale, 9);
        if (event.caster instanceof ServerPlayer player) {
            var text = String.format("%.2f -> %.2f", scaleData.getScale(), newScale);
            var message = new TextComponent(text).withStyle(ChatFormatting.GREEN);
            player.sendMessage(message, ChatType.GAME_INFO, NIL_UUID);
        }

        scaleData.setTargetScale(newScale);

        var duration = AbilityPowerUtil.scale(event.abilityPower, 20 * 60, 20 * 60 * 10);
        RestoreSizeTickable.refresh(entity, duration);
    }
}
