package network.something.somepotter.spell.spells.aguamenti;

import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellCastListener;
import network.something.somepotter.spell.cast.projectile_or_self.ProjectileOrSelfCast;

public class AguamentiCastListener extends SpellCastListener {

    public AguamentiCastListener() {
        super(AguamentiSpell.ID);
    }

    @Override
    public void onSpellCast(SpellCastEvent event) {
        var cast = new ProjectileOrSelfCast();
        cast.execute(event.getCaster(), event.getSpellId());
    }
}
