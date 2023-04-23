package network.something.somepotter.spell.spells.confringo;

import network.something.somepotter.spell.api.cast.ProjectileCast;
import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellCastListener;

public class ConfringoCastListener extends SpellCastListener {

    public ConfringoCastListener() {
        super(ConfringoSpell.ID);
    }

    @Override
    public void onSpellCast(SpellCastEvent event) {
        var cast = new ProjectileCast();
        cast.execute(event.getCaster(), event.getSpellId());
    }
}
