package network.something.somepotter.spell.aguamenti;

import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.event.SpellCastEvent;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AguamentiListener {

    @SubscribeEvent
    public static void onSpellCast(SpellCastEvent.Post<AguamentiSpell> event) {
        if (!event.is(AguamentiSpell.ID)) return;

        var text = new TextComponent("AGUAMENTI!");
        event.player.sendMessage(text, event.player.getUUID());
        SomePotter.LOGGER.info("AGUAMENTI! {}", event.spell.getId());
    }

}
