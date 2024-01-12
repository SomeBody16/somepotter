package network.something.somepotter.spell.protego;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.init.EffectInit;
import network.something.somepotter.spell.SpellListener;

public class ProtegoListener extends SpellListener<ProtegoSpell> {

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<ProtegoSpell> event, EntityHitResult hitResult, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            var effect = new MobEffectInstance(EffectInit.PROTEGO.get(), 20 * 5, 0);
            livingEntity.addEffect(effect);
        }
    }
}
