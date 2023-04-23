package network.something.somepotter.spell.spells.disillusio;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;

public class DisillusioHitListener extends SpellHitListener {
    public DisillusioHitListener() {
        super(DisillusioSpell.ID);
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            var effect = new MobEffectInstance(MobEffects.INVISIBILITY, DisillusioSpell.DURATION);
            livingEntity.addEffect(effect);
        }
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
    }
}
