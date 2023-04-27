package network.something.somepotter.spell.spells.levioso;

import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellCastListener;
import network.something.somepotter.spell.cast.touch.TouchCast;

public class LeviosoCastListener extends SpellCastListener {

    public LeviosoCastListener() {
        super(LeviosoSpell.ID);
    }

    @Override
    public void onSpellCast(SpellCastEvent event) {
        var cast = new TouchCast();
        cast.setRange(LeviosoSpell.RANGE);
        cast.execute(event.getCaster(), event.getSpellId());
    }
}
