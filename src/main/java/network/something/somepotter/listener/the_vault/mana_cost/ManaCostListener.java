package network.something.somepotter.listener.the_vault.mana_cost;

import iskallia.vault.init.ModSounds;
import iskallia.vault.mana.Mana;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.listener.the_vault.TheVaultConfig;
import network.something.somepotter.util.AbilityPowerUtil;
import network.something.somepotter.util.IntegrationUtil;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ManaCostListener {

    @SubscribeEvent
    public static void onSpellCast(SpellCastEvent.Pre<?> event) {
        IntegrationUtil.theVault(() -> {
            if (event.caster instanceof ServerPlayer player) {

                var config = TheVaultConfig.get();
                var manaCostPercent = config.manaCosts.getOrDefault(event.spell.getId(), config.manaCostDefault);
                manaCostPercent += AbilityPowerUtil.scale(event.abilityPower, manaCostPercent);
                manaCostPercent = Math.min(manaCostPercent, 1F);

                var manaCost = Mana.getMax(player) * manaCostPercent;
                var playerMana = Mana.get(player);
                if (playerMana < manaCost) {
                    var sound = ModSounds.ABILITY_OUT_OF_MANA;
                    event.level.playSound(null, event.caster, sound, SoundSource.PLAYERS, 1.0F, 1.0F);
                    event.setCanceled(true);
                    return;
                }

                Mana.decrease(player, manaCost);
            }
        });
    }

}
