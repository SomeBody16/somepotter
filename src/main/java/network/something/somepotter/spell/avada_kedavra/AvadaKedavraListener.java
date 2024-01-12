package network.something.somepotter.spell.avada_kedavra;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spell.SpellListener;

public class AvadaKedavraListener extends SpellListener<AvadaKedavraSpell> {

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<AvadaKedavraSpell> event, EntityHitResult hitResult, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            var damageSource = event.spell.getDamageSource(event.caster);
            livingEntity.hurt(damageSource, Float.MAX_VALUE);
        }
    }
}
