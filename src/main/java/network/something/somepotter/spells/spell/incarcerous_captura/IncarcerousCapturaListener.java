package network.something.somepotter.spells.spell.incarcerous_captura;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.tickable.Tickables;

public class IncarcerousCapturaListener extends SpellListener<IncarcerousCapturaSpell> {

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<IncarcerousCapturaSpell> event, EntityHitResult hitResult, Entity entity) {
        if (event.caster instanceof ServerPlayer player
                && entity instanceof LivingEntity livingEntity) {
            var tickable = new CapturingProcessTickable(event.spell, event.abilityPower, player, livingEntity);
            Tickables.add(tickable);
        }
    }

    @Override
    public void onSpellHitPlayer(SpellHitEvent.Post<IncarcerousCapturaSpell> event, EntityHitResult hitResult, ServerPlayer player) {
    }
}
