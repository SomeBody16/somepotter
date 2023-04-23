package network.something.somepotter.spell.spells.cistem_aperio;

import network.something.somepotter.spell.api.cast.TouchCast;
import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellCastListener;

public class CistemAperioCastListener extends SpellCastListener {

    public CistemAperioCastListener() {
        super(CistemAperioSpell.ID);
    }

    @Override
    public void onSpellCast(SpellCastEvent event) {
        var cast = new TouchCast();
        cast.execute(event.getCaster(), event.getSpellId());
    }
}
