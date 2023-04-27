package network.something.somepotter.spell.spells.basic_cast;

import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellCastListener;
import network.something.somepotter.spell.cast.projectile.ProjectileCast;

public class BasicCastCastListener extends SpellCastListener {

    public BasicCastCastListener() {
        super(BasicCastSpell.ID);
    }

    @Override
    public void onSpellCast(SpellCastEvent event) {
        var cast = new ProjectileCast();
        cast.setSpeed(2f);
        cast.setRange(32);
        cast.execute(event.getCaster(), event.getSpellId());
    }
}
