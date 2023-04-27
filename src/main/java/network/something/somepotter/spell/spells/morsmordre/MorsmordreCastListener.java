package network.something.somepotter.spell.spells.morsmordre;

import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellCastListener;
import network.something.somepotter.spell.cast.missile.MissileCast;

public class MorsmordreCastListener extends SpellCastListener {

    public MorsmordreCastListener() {
        super(MorsmordreSpell.ID);
    }

    @Override
    public void onSpellCast(SpellCastEvent event) {
        if (event.getCaster().getXRot() < -0.75) {
            var cast = new MissileCast();
            cast.setLifeTime(25);
            cast.execute(event.getCaster(), event.getSpellId());
        }
    }
}
