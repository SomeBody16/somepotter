package network.something.somepotter.spell.spells.crucio;

import network.something.somepotter.spell.api.cast.TouchCast;
import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellCastListener;

public class CrucioCastListener extends SpellCastListener {

    public CrucioCastListener() {
        super(CrucioSpell.ID);
    }

    @Override
    public void onSpellCast(SpellCastEvent event) {
        var cast = new TouchCast();
        cast.execute(event.getCaster(), event.getSpellId());
    }
}
