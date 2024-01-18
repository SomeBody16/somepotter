package network.something.somepotter.integration.the_vault.listener;

import iskallia.vault.util.calc.AreaOfEffectHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.util.IntegrationUtil;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AreaOfEffectListener {

    @SubscribeEvent
    public static void onSpellCast(SpellCastEvent.Pre<?> event) {
        IntegrationUtil.theVault(() -> {
            event.areaOfEffect = AreaOfEffectHelper.adjustAreaOfEffect(event.caster, event.areaOfEffect);
        });
    }

}
