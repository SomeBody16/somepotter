package network.something.somepotter.integration.the_vault.listener;

import iskallia.vault.util.calc.CooldownHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.mechanics.cooldown.CooldownManager;
import network.something.somepotter.util.IntegrationUtil;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CooldownReductionListener {

    @SubscribeEvent
    public static void onSpellCast(SpellCastEvent.Post<?> event) {
        IntegrationUtil.theVault(() -> {
            var cooldown = CooldownManager.getRemainingTicks(event.caster.getUUID(), event.spell.getId());
            cooldown -= (int) (cooldown * CooldownHelper.getCooldownMultiplier(event.caster));
            CooldownManager.set(event.caster.getUUID(), event.spell.getId(), cooldown);
        });
    }
}
