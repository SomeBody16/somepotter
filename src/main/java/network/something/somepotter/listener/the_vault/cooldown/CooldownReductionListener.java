package network.something.somepotter.listener.the_vault.cooldown;

import iskallia.vault.util.calc.CooldownHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.listener.cooldown.CooldownManager;
import network.something.somepotter.util.IntegrationUtil;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CooldownReductionListener {

    @SubscribeEvent
    public static void onSpellCast(SpellCastEvent.Post<?> event) {
        IntegrationUtil.theVault(() -> {
            var cooldown = CooldownManager.get(event.caster.getUUID(), event.spell.getId());
            cooldown -= (int) (cooldown * CooldownHelper.getCooldownMultiplier(event.caster));
            CooldownManager.set(event.caster.getUUID(), event.spell.getId(), cooldown);
        });
    }
}
