package network.something.somepotter.mechanics.cooldown;

import iskallia.vault.init.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.util.IntegrationUtil;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CooldownListener {

    @SubscribeEvent
    public static void onSpellCast(SpellCastEvent.Pre<Spell> event) {
        var remainingTicks = CooldownManager.getRemainingTicks(event.caster.getUUID(), event.spell.getId());
        if (remainingTicks > 0) {
            event.setCanceled(true);
            event.level.playSound(null, event.caster, getCooldownSound(), SoundSource.PLAYERS, 1.0F, 1.0F);
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
