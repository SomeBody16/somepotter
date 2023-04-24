package network.something.somepotter.spell.spells;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.spell.api.event.SpellCastEvent;

import java.util.HashMap;
import java.util.Map;

import static net.minecraft.Util.NIL_UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SomePotter.MOD_ID)
public class CooldownManager {

    private static final Map<LivingEntity, Map<String, Integer>> COOLDOWNS = new HashMap<>();

    @SubscribeEvent
    public static void onCast(SpellCastEvent event) {
        var caster = event.getCaster();
        var spellId = event.getSpellId();

        var playerCooldowns = COOLDOWNS
                .getOrDefault(caster, new HashMap<>());

        var cooldownTicks = playerCooldowns.getOrDefault(spellId, 0);
        if (cooldownTicks > 0) {
            event.setCanceled(true);
            if (caster instanceof ServerPlayer player) {
                var secondsLeft = cooldownTicks / 20;
                var message = new TextComponent(spellId + " is on cooldown : " + secondsLeft)
                        .withStyle(ChatFormatting.RED)
                        .withStyle(ChatFormatting.ITALIC);
                player.sendMessage(message, NIL_UUID);
            }
            return;
        }

        playerCooldowns.put(spellId, event.getSpell().getCooldown());
        COOLDOWNS.put(caster, playerCooldowns);
    }

    @SubscribeEvent
    public static void serverTick(TickEvent.ServerTickEvent event) {
        COOLDOWNS.forEach((uuid, playerCooldowns) -> {
            playerCooldowns.forEach((spellId, remainingTicks) -> {
                playerCooldowns.put(spellId, remainingTicks - 1);
            });
        });
    }

}
