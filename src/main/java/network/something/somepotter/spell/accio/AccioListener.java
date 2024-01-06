package network.something.somepotter.spell.accio;

import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.event.SpellCastEvent;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AccioListener {

    @SubscribeEvent
    public static void onSpellCast(SpellCastEvent.Post<AccioSpell> event) {
        if (!event.is(AccioSpell.ID)) return;

        var text = new TextComponent("ACCIO!");
        event.player.sendMessage(text, event.player.getUUID());
        SomePotter.LOGGER.info("ACCIO! {}", event.spell.getId());
    }

}
