package network.something.somepotter.listener.cooldown;

import iskallia.vault.init.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.util.IntegrationUtil;

import static net.minecraft.Util.NIL_UUID;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CooldownListener {

    @SubscribeEvent
    public static void onSpellCast(SpellCastEvent.Pre<Spell> event) {

        var remainingTicks = CooldownManager.get(event.caster.getUUID(), event.spell.getId());
        if (remainingTicks > 0) {
            event.setCanceled(true);
            event.level.playSound(null, event.caster, getCooldownSound(), SoundSource.PLAYERS, 1.0F, 1.0F);

            if (event.caster instanceof ServerPlayer player) {
                var seconds = (int) Math.ceil(remainingTicks / 20.0);
                var text = new TextComponent(seconds + "s remaining").withStyle(ChatFormatting.RED);
                player.sendMessage(text, ChatType.GAME_INFO, NIL_UUID);
            }

            return;
        }

        var config = CooldownConfig.get();
        var cooldown = config.cooldown.getOrDefault(event.spell.getId(), config.cooldownDefault);
        CooldownManager.set(event.caster.getUUID(), event.spell.getId(), cooldown);
    }

    public static SoundEvent getCooldownSound() {
        if (IntegrationUtil.theVault()) {
            return ModSounds.ABILITY_ON_COOLDOWN;
        }
        return SoundEvents.DISPENSER_FAIL;
    }

}
