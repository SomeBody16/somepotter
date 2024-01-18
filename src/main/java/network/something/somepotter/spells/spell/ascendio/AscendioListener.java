package network.something.somepotter.spells.spell.ascendio;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell.alarte_ascendare.AlarteAscendareListener;

public class AscendioListener extends SpellListener<AscendioSpell> {

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<AscendioSpell> event, EntityHitResult hitResult, Entity entity) {
        AlarteAscendareListener.applyForce(entity, event.abilityPower);
    }
}
