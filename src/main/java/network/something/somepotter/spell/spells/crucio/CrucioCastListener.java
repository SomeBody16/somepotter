package network.something.somepotter.spell.spells.crucio;

import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellCastListener;
import network.something.somepotter.spell.cast.touch.TouchCast;

public class CrucioCastListener extends SpellCastListener {

    public CrucioCastListener() {
        super(CrucioSpell.ID);
    }

    @Override
    public void onSpellCast(SpellCastEvent event) {
        var cast = new TouchCast();
        cast.setRange(CrucioSpell.RANGE);
        cast.execute(event.getCaster(), event.getSpellId());
    }
}
