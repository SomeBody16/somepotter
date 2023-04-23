package network.something.somepotter.spell.spells.accio;

import network.something.somepotter.spell.api.cast.TouchCast;
import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellCastListener;

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
