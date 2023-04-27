package network.something.somepotter.spell.spells.ascendio;

import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellCastListener;
import network.something.somepotter.spell.cast.projectile.ProjectileCast;
import network.something.somepotter.spell.cast.self.SelfCast;

public class AscendioCastListener extends SpellCastListener {

    public AscendioCastListener() {
        super(AscendioSpell.ID);
    }

    @Override
    public void onSpellCast(SpellCastEvent event) {
        if (event.getCaster().getXRot() < -0.75) {
            new SelfCast().execute(event.getCaster(), event.getSpellId());
            return;
        }

        var cast = new ProjectileCast();
        cast.execute(event.getCaster(), event.getSpellId());
    }
}
