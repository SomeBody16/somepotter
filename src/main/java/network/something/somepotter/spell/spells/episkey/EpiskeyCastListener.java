package network.something.somepotter.spell.spells.episkey;

import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellCastListener;
import network.something.somepotter.spell.cast.projectile_or_self.ProjectileOrSelfCast;

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
