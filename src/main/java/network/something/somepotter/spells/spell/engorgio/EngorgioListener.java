package network.something.somepotter.spells.spell.engorgio;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.init.EffectInit;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.util.AbilityPowerUtil;

public class EngorgioListener extends SpellListener<EngorgioSpell> {

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<EngorgioSpell> event, EntityHitResult hitResult, Entity entity) {
        if (entity instanceof LivingEntity target) {
            var duration = AbilityPowerUtil.scale(event.abilityPower, 20 * 60, 20 * 60 * 10);
            var effect = new MobEffectInstance(EffectInit.ENGORGIO.get(), duration, 0, false, false, true);
            event.addEffect(target, effect);
        }
    }
}
