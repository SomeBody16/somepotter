package network.something.somepotter.spell.spells.obscuro;

import network.something.somepotter.spell.api.cast.ProjectileCast;
import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellCastListener;

public class ObscuroCastListener extends SpellCastListener {

    public ObscuroCastListener() {
        super(ObscuroSpell.ID);
    }

    @Override
    public void onSpellCast(SpellCastEvent event) {
        var cast = new ProjectileCast();
        cast.execute(event.getCaster(), event.getSpellId());
    }
}
