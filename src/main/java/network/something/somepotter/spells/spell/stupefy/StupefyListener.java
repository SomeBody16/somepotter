package network.something.somepotter.spells.spell.stupefy;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell.depulso.DepulsoListener;
import network.something.somepotter.util.AbilityPowerUtil;

public class StupefyListener extends SpellListener<StupefySpell> {

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<StupefySpell> event, EntityHitResult hitResult, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            var duration = AbilityPowerUtil.scale(event.abilityPower, 20, 20 * 4);

            var blindness = new MobEffectInstance(MobEffects.BLINDNESS, duration, 0, false, false, true);
            livingEntity.addEffect(blindness, event.caster);

            var nausea = new MobEffectInstance(MobEffects.CONFUSION, duration, 0, false, false, true);
            livingEntity.addEffect(nausea, event.caster);
        }

        var pushStrength = AbilityPowerUtil.scale(event.abilityPower, 1F, 2F);
        DepulsoListener.push(event.caster, entity, pushStrength);
    }

    @Override
    public void onSpellHitPlayer(SpellHitEvent.Post<StupefySpell> event, EntityHitResult hitResult, ServerPlayer player) {
        new StupefyPacket().sendToClient(player);
        onSpellHitEntity(event, hitResult, player);
    }
}
