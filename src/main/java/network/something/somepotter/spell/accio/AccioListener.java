package network.something.somepotter.spell.accio;

import net.minecraft.network.chat.TextComponent;
import network.something.somepotter.SomePotter;
import network.something.somepotter.cast.touch.TouchCast;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.spell.SpellListener;

public class AccioListener extends SpellListener<AccioSpell> {

    @Override
    public void onSpellCast(SpellCastEvent.Post<AccioSpell> event) {
        var text = new TextComponent("ACCIO!");
        event.caster.sendMessage(text, event.caster.getUUID());
        SomePotter.LOGGER.info("ACCIO! {}", event.spell.getId());

        new TouchCast(event).execute();
    }

}
