package network.something.somepotter.spell.spells.incendio;

import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellCastListener;
import network.something.somepotter.spell.cast.projectile.ProjectileCast;

public class IncendioCastListener extends SpellCastListener {

    public IncendioCastListener() {
        super(IncendioSpell.ID);
    }

    @Override
    public void onSpellCast(SpellCastEvent event) {
        var cast = new ProjectileCast();
        cast.execute(event.getCaster(), event.getSpellId());
    }
}
