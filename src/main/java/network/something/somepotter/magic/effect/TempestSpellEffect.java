package network.something.somepotter.magic.effect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.AABB;
import network.something.somepotter.magic.effect.core.AreaOfEffectSpellEffect;
import network.something.somepotter.magic.spell.core.Spell;
import network.something.somepotter.util.ParticleUtil;

public class TempestSpellEffect extends AreaOfEffectSpellEffect {
    public TempestSpellEffect(Spell spell, AABB areaOfEffect) {
        super(spell, areaOfEffect, 20 * 10);
    }

    @Override
    public void tick() {
        if (duration % 20 == 0) {
            strike();
        }
        playParticles();
    }

    public void strike() {
        var entities = spell.caster.level.getEntities(
                spell.caster,
                areaOfEffect,
                e -> spell.caster.level.canSeeSky(e.getOnPos())
        );
        if (entities.isEmpty()) {
            return;
        }
        duration += spell.caster.level.random.nextInt(10, 15 + (entities.size() * 5));
        var entity = entities.get(spell.caster.level.random.nextInt(entities.size()));

        var lightingEntity = EntityType.LIGHTNING_BOLT.create(spell.caster.level);
        assert lightingEntity != null;
        lightingEntity.setPos(entity.getEyePosition());
        spell.caster.level.addFreshEntity(lightingEntity);
    }

    public void playParticles() {
        ParticleUtil.inAABBOfAmount(level, ParticleTypes.ENCHANT, areaOfEffect, 50);
    }
}
