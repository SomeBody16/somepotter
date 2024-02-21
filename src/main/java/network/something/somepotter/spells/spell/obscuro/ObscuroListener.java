package network.something.somepotter.spells.spell.obscuro;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.util.AbilityPowerUtil;

public class ObscuroListener extends SpellListener<ObscuroSpell> {

    @Override
    public void onSpellHitPlayer(SpellHitEvent.Post<ObscuroSpell> event, EntityHitResult hitResult, ServerPlayer player) {
        var duration = AbilityPowerUtil.scale(event.abilityPower, 20 * 5, 20 * 30);
        var effect = new MobEffectInstance(MobEffects.BLINDNESS, duration, 2, false, true, true);
        player.addEffect(effect, event.caster);
    }
}
