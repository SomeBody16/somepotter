package network.something.somemagic.magic.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import network.something.somemagic.SomeMagic;
import network.something.somemagic.magic.effect.core.EntitySpellEffect;
import network.something.somemagic.magic.spell.CrucioSpell;
import network.something.somemagic.magic.spell.core.Spell;
import network.something.somemagic.util.DamageSourceUtil;

public class CrucioSpellEffect extends EntitySpellEffect {

    public CrucioSpellEffect(Spell spell, Entity target) {
        super(spell, target, 0);
    }

    private int age = 0;

    @Override
    public void tick() {
        if (target instanceof LivingEntity livingTarget
                && age++ % 5 == 0
        ) {
            SomeMagic.LOGGER.info("Triggering CRUCIO");
            var damageSource = DamageSourceUtil.indirect("spell." + CrucioSpell.ID, spell.caster);
            livingTarget.hurt(damageSource, 0);

            var effect = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 10, true, false, false);
            livingTarget.addEffect(effect);

            var pos = target.getEyePosition();
            var level = (ServerLevel) spell.caster.level;
            level.sendParticles(
                    spell.getColor().getParticle(),
                    pos.x, pos.y, pos.z,
                    25,
                    1, 1, 1,
                    0.01
            );
        }
    }

    @Override
    public boolean isExpired() {
        return isLookingAtTarget();
    }

    protected boolean isLookingAtTarget() {
        Vec3 vec3 = spell.caster.getViewVector(1.0F).normalize();
        Vec3 vec31 = new Vec3(
                target.getX() - spell.caster.getX(),
                target.getEyeY() - spell.caster.getEyeY(),
                target.getZ() - spell.caster.getZ());
        double d0 = vec31.length();
        vec31 = vec31.normalize();
        double d1 = vec3.dot(vec31);

        return d1 > 1.0D - 0.025D / d0 && spell.caster.hasLineOfSight(target);
    }
}
