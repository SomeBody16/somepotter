package network.something.somepotter.spell.spells.episkey;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;

public class EpiskeyHitListener extends SpellHitListener {
    public EpiskeyHitListener() {
        super(EpiskeySpell.ID);
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            var healthRatio = livingEntity.getHealth() / livingEntity.getMaxHealth();
            if (healthRatio >= EpiskeySpell.HEAL_IF_ABOVE) {
                var effect = new MobEffectInstance(MobEffects.REGENERATION,
                        EpiskeySpell.HEAL_DURATION, EpiskeySpell.HEAL_AMPLIFIER);
                livingEntity.addEffect(effect);
            }
        }
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
    }
}
