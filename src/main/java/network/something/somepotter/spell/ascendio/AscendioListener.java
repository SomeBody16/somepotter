package network.something.somepotter.spell.ascendio;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.cast.self.SelfCast;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.spell.alarte_ascendare.AlarteAscendareListener;

public class AscendioListener extends SpellListener<AscendioSpell> {

    @Override
    public void onSpellCast(SpellCastEvent.Post<AscendioSpell> event) {
        new SelfCast(event).execute();
    }

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<AscendioSpell> event, EntityHitResult hitResult, Entity entity) {
        AlarteAscendareListener.applyForce(entity, event.abilityPower);
    }
}
