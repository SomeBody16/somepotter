package network.something.somepotter.spell.aguamenti;

import net.minecraft.network.chat.TextComponent;
import network.something.somepotter.SomePotter;
import network.something.somepotter.cast.touch.TouchCast;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.spell.SpellListener;

public class AguamentiListener extends SpellListener<AguamentiSpell> {

    @Override
    public void onSpellCast(SpellCastEvent.Post<AguamentiSpell> event) {
        var text = new TextComponent("AGUAMENTI!");
        event.caster.sendMessage(text, event.caster.getUUID());
        SomePotter.LOGGER.info("AGUAMENTI! {}", event.spell.getId());

        new TouchCast(event).execute();
    }

}
