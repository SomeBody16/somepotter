package network.something.somepotter.spell.basic_cast;

import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.event.SpellCastEvent;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BasicCastListener {

    @SubscribeEvent
    public static void onSpellCast(SpellCastEvent.Post<BasicCastSpell> event) {
        if (!event.is(BasicCastSpell.ID)) return;

        var text = new TextComponent("BASIC CAST!");
        event.player.sendMessage(text, event.player.getUUID());
        SomePotter.LOGGER.info("ACCIO! {}", event.spell.getId());
    }

}
