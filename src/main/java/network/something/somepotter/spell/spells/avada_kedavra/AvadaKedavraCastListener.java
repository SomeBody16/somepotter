package network.something.somepotter.spell.spells.avada_kedavra;

import network.something.somepotter.spell.api.cast.ProjectileCast;
import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellCastListener;

public class AvadaKedavraCastListener extends SpellCastListener {
    public AvadaKedavraCastListener() {
        super(AvadaKedavraSpell.ID);
    }

    @Override
    public void onSpellCast(SpellCastEvent event) {
        var cast = new ProjectileCast();
        cast.execute(event.getCaster(), event.getSpellId());
    }
}
