package network.something.somepotter.spell.spells.stupefy;

import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellCastListener;
import network.something.somepotter.spell.cast.projectile.ProjectileCast;

public class StupefyCastListener extends SpellCastListener {

    public StupefyCastListener() {
        super(StupefySpell.ID);
    }

    @Override
    public void onSpellCast(SpellCastEvent event) {
        var cast = new ProjectileCast();
        cast.execute(event.getCaster(), event.getSpellId());
    }
}
