package network.something.somepotter.spell.spells.tempest;

import network.something.somepotter.spell.api.cast.TouchCast;
import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellCastListener;

public class TempestCastListener extends SpellCastListener {

    public TempestCastListener() {
        super(TempestSpell.ID);
    }

    @Override
    public void onSpellCast(SpellCastEvent event) {
        var cast = new TouchCast();
        cast.execute(event.getCaster(), event.getSpellId());
    }
}
