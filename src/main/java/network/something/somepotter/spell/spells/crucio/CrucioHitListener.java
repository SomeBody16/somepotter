package network.something.somepotter.spell.spells.crucio;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;
import network.something.somepotter.spell.effect.SpellEffects;

public class CrucioHitListener extends SpellHitListener {
    public CrucioHitListener() {
        super(CrucioSpell.ID);
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
        var crucio = new MobEffectInstance(SpellEffects.CRUCIO.get(), CrucioSpell.REFRESH_DURATION, 10);
        var crucioApply = new MobEffectInstance(SpellEffects.CRUCIO_APPLY.get(),
                CrucioSpell.REFRESH_DURATION, 0, true, false, false);

        if (event.getEntity() instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(crucio);
            event.getCaster().addEffect(crucioApply);
        }
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
    }
}
