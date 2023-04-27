package network.something.somepotter.spell.spells.accio;

import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellCastListener;
import network.something.somepotter.spell.cast.touch.TouchCast;

public class AccioCastListener extends SpellCastListener {
    public AccioCastListener() {
        super(AccioSpell.ID);
    }

    @Override
    public void onSpellCast(SpellCastEvent event) {
        var cast = new TouchCast();
        cast.execute(event.getCaster(), event.getSpellId());
    }
}
