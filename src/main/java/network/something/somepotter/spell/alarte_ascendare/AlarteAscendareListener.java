package network.something.somepotter.spell.alarte_ascendare;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.cast.projectile.ProjectileCast;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.util.AbilityPowerUtil;

public class AlarteAscendareListener extends SpellListener<AlarteAscendareSpell> {

    public static void applyForce(Entity entity, int abilityPower) {
        var force = AbilityPowerUtil.scale(abilityPower, 1.0F, 3.0F);
        var motion = entity.getDeltaMovement().add(0, force, 0);
        entity.setDeltaMovement(motion);
        entity.fallDistance = 0;
        entity.hurtMarked = true;
    }


    @Override
    public void onSpellCast(SpellCastEvent.Post<AlarteAscendareSpell> event) {
        new ProjectileCast(event).execute();
    }

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<AlarteAscendareSpell> event, EntityHitResult hitResult, Entity entity) {
        applyForce(entity, event.abilityPower);
    }
}
