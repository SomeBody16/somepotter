package network.something.somepotter.spell.basic_cast;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.cast.touch.TouchCast;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.util.AbilityPowerUtil;

public class BasicCastListener extends SpellListener<BasicCastSpell> {

    @Override
    public void onSpellCast(SpellCastEvent.Post<BasicCastSpell> event) {
        new TouchCast(event).execute();
    }

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<BasicCastSpell> event, EntityHitResult hitResult) {
        var config = BasicCastSpell.CONFIG.get();
        var entity = hitResult.getEntity();
        var damageSource = event.spell.getDamageSource(event.caster);
        var damage = Math.max(config.damageMin, event.abilityPower * config.mobDamageMultiplier);
        entity.hurt(damageSource, damage);
    }

    @Override
    public void onSpellHitPlayer(SpellHitEvent.Post<BasicCastSpell> event, EntityHitResult hitResult, ServerPlayer player) {
        var config = BasicCastSpell.CONFIG.get();
        var damageSource = event.spell.getDamageSource(event.caster);
        var damage = AbilityPowerUtil.scale(event.abilityPower, config.damageMin, config.playerDamageMax);
        player.hurt(damageSource, damage);
    }
}
