package network.something.somepotter.spell.spells.alohomora;

import network.something.somepotter.spell.api.cast.TouchCast;
import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellCastListener;

public class AlohomoraCastListener extends SpellCastListener {

    public AlohomoraCastListener() {
        super(AlohomoraSpell.ID);
    }

    @Override
    public void onSpellCast(SpellCastEvent event) {
        var cast = new TouchCast();
        cast.execute(event.getCaster(), event.getSpellId());
    }
}
