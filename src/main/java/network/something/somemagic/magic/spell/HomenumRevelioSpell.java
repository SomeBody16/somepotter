package network.something.somemagic.magic.spell;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import network.something.somemagic.magic.spell.core.SelfSpell;
import network.something.somemagic.util.SpellColor;

public class HomenumRevelioSpell extends SelfSpell {
    public static final String ID = "homenum_revelio";

    public HomenumRevelioSpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public int getCooldownTicks() {
        return 20 * 20;
    }

    @Override
    public void onCast() {
        var range = 25;
        var areaOfEffect = caster.getBoundingBox().inflate(range);

        var glowingEffect = new MobEffectInstance(MobEffects.GLOWING, 20 * 6);

        for (var entity : caster.level.getEntities(caster, areaOfEffect)) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(glowingEffect);
            }
        }
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.UTILITY;
    }

}
