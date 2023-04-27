package network.something.somepotter.spell.spells.colloportus;

import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellCastListener;
import network.something.somepotter.spell.cast.touch.TouchCast;

public class ColloportusCastListener extends SpellCastListener {

    public ColloportusCastListener() {
        super(ColloportusSpell.ID);
    }

    @Override
    public void onSpellCast(SpellCastEvent event) {
        var cast = new TouchCast();
        cast.execute(event.getCaster(), event.getSpellId());
    }
}
