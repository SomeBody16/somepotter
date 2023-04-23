package network.something.somepotter.spell.spells.fumos;

import network.something.somepotter.spell.api.cast.ProjectileOrSelfCast;
import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellCastListener;

public class FumosCastListener extends SpellCastListener {

    public FumosCastListener() {
        super(FumosSpell.ID);
    }

    @Override
    public void onSpellCast(SpellCastEvent event) {
        var cast = new ProjectileOrSelfCast();
        cast.execute(event.getCaster(), event.getSpellId());
    }
}
