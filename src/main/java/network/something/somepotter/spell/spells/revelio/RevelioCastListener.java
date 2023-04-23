package network.something.somepotter.spell.spells.revelio;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellCastListener;

public class RevelioCastListener extends SpellCastListener {

    public RevelioCastListener() {
        super(RevelioSpell.ID);
    }

    @Override
    public void onSpellCast(SpellCastEvent event) {
        var areaOfEffect = event.getCaster().getBoundingBox()
                .inflate(RevelioSpell.RANGE);

        for (var entity : event.getCaster().level.getEntities(event.getCaster(), areaOfEffect)) {
            if (entity instanceof LivingEntity livingEntity) {
                var glowingEffect = new MobEffectInstance(MobEffects.GLOWING, RevelioSpell.DURATION,
                        0, false, false, false);
                livingEntity.addEffect(glowingEffect);
            }
        }
    }
}
