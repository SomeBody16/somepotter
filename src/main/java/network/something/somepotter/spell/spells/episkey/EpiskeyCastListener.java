package network.something.somepotter.spell.spells.episkey;

import network.something.somepotter.spell.api.cast.ProjectileOrSelfCast;
import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellCastListener;

public class EpiskeyCastListener extends SpellCastListener {

    public EpiskeyCastListener() {
        super(EpiskeySpell.ID);
    }

    @Override
    public void onSpellCast(SpellCastEvent event) {
        var cast = new ProjectileOrSelfCast();
        cast.execute(event.getCaster(), event.getSpellId());
    }
}
