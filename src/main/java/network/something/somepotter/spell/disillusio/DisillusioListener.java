package network.something.somepotter.spell.disillusio;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.tickable.Tickables;
import network.something.somepotter.util.AbilityPowerUtil;

public class DisillusioListener extends SpellListener<DisillusioSpell> {

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<DisillusioSpell> event, EntityHitResult hitResult, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {

            var duration = AbilityPowerUtil.scale(event.abilityPower, 20 * 10, 20 * 60 * 2);
            var tickable = new DisillusioTickable(duration, livingEntity);
            Tickables.add(tickable);
        }
    }
}
