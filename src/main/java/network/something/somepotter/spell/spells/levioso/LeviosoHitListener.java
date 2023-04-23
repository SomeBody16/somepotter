package network.something.somepotter.spell.spells.levioso;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;

public class LeviosoHitListener extends SpellHitListener {
    public LeviosoHitListener() {
        super(LeviosoSpell.ID);
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
        if (event.getEntity() instanceof LivingEntity hitEntity) {
            var effect = new MobEffectInstance(MobEffects.LEVITATION,
                    LeviosoSpell.DURATION, 1, true, false, false);
            hitEntity.addEffect(effect);
        }
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
    }
}
