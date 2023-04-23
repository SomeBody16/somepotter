package network.something.somepotter.spell.spells.ascendio;

import network.something.somepotter.SomePotter;
import network.something.somepotter.spell.api.cast.ProjectileCast;
import network.something.somepotter.spell.api.cast.SelfCast;
import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellCastListener;

public class AscendioCastListener extends SpellCastListener {

    public AscendioCastListener() {
        super(AscendioSpell.ID);
    }

    @Override
    public void onSpellCast(SpellCastEvent event) {
        SomePotter.LOGGER.info("Caster yRot: {}", event.getCaster().getYRot());
        SomePotter.LOGGER.info("Caster xRot: {}", event.getCaster().getXRot());
        SomePotter.LOGGER.info("Caster getLookAngle: {}", event.getCaster().getLookAngle());
        if (event.getCaster().getXRot() < -0.75) {
            new SelfCast().execute(event.getCaster(), event.getSpellId());
            return;
        }

        var cast = new ProjectileCast();
        cast.execute(event.getCaster(), event.getSpellId());
    }
}
