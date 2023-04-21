package network.something.somemagic.magic.spell;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somemagic.entity.SpellEntity;
import network.something.somemagic.magic.spell.core.TouchSpell;
import network.something.somemagic.util.SpellColor;

public class WingardiumLeviosaSpell extends TouchSpell {
    public static final String ID = "wingardium_leviosa";

    public WingardiumLeviosaSpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public int getCooldownTicks() {
        return 15;
    }

    @Override
    public void onHitEntity(SpellEntity spellEntity, EntityHitResult hitResult) {
        super.onHitEntity(spellEntity, hitResult);
        if (hitResult.getEntity() instanceof LivingEntity hitEntity) {
            var effect = new MobEffectInstance(MobEffects.LEVITATION, 20 * 10, 1, true, false, false);
            hitEntity.addEffect(effect);
        }
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.FORCE;
    }
}

