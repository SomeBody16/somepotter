package network.something.somepotter.spell.spells.bombarda;

import network.something.somepotter.spell.api.cast.ProjectileCast;
import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellCastListener;

import java.util.List;

public class BombardaCastListener extends SpellCastListener {

    public BombardaCastListener() {
        super(List.of(
                BombardaSpell.BOMBARDA.getId(),
                BombardaSpell.BOMBARDA_MAXIMA.getId()
        ));
    }

    @Override
    public void onSpellCast(SpellCastEvent event) {
        var cast = new ProjectileCast();
        cast.execute(event.getCaster(), event.getSpellId());
    }
}
