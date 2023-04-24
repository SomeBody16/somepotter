package network.something.somepotter.spell.spells.obscuro;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;

public class ObscuroHitListener extends SpellHitListener {
    public ObscuroHitListener() {
        super(ObscuroSpell.ID);
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            var blindness = new MobEffectInstance(MobEffects.BLINDNESS,
                    ObscuroSpell.DURATION);
            livingEntity.addEffect(blindness);
        }
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
    }
}
