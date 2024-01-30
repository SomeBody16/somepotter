package network.something.somepotter.spells.spell.basic_cast;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.mechanics.spell_point.SpellPointData;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.util.AbilityPowerUtil;

public class BasicCastListener extends SpellListener<BasicCastSpell> {

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<BasicCastSpell> event, EntityHitResult hitResult, Entity entity) {
        var config = BasicCastSpell.CONFIG.get();
        var damageSource = event.spell.getDamageSource(event.caster);
        var damage = Math.max(config.damageMin, event.abilityPower * config.mobDamageMultiplier);
        entity.hurt(damageSource, damage);

        SpellPointData.add((ServerPlayer) event.caster, 3);
    }

    @Override
    public void onSpellHitPlayer(SpellHitEvent.Post<BasicCastSpell> event, EntityHitResult hitResult, ServerPlayer player) {
        var config = BasicCastSpell.CONFIG.get();
        var damageSource = event.spell.getDamageSource(event.caster);
        var damage = AbilityPowerUtil.scale(event.abilityPower, config.damageMin, config.playerDamageMax);
        player.hurt(damageSource, damage);
    }
}
