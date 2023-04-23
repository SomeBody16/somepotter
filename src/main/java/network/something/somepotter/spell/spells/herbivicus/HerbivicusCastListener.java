package network.something.somepotter.spell.spells.herbivicus;

import network.something.somepotter.spell.api.cast.ProjectileCast;
import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellCastListener;

public class HerbivicusCastListener extends SpellCastListener {

    public HerbivicusCastListener() {
        super(HerbivicusSpell.ID);
    }

    @Override
    public void onSpellCast(SpellCastEvent event) {
        var cast = new ProjectileCast();
        cast.execute(event.getCaster(), event.getSpellId());
    }
}
