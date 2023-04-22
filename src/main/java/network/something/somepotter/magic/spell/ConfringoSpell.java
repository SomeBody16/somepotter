package network.something.somepotter.magic.spell;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.entity.SpellEntity;
import network.something.somepotter.magic.spell.core.ProjectileSpell;
import network.something.somepotter.util.ParticleUtil;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class ConfringoSpell extends ProjectileSpell {
    public static final String ID = "confringo";

    public ConfringoSpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public int getCooldownTicks() {
        return 20 * 10;
    }

    @Override
    public void onHitEntity(SpellEntity spellEntity, EntityHitResult hitResult) {
        var level = spellEntity.level;
        var hitEntity = hitResult.getEntity();

        AABB areaOfEffect = hitEntity.getBoundingBox().inflate(2);
        explode(level, hitEntity.getEyePosition(), areaOfEffect);
    }

    @Override
    public void onHitBlock(SpellEntity spellEntity, BlockHitResult hitResult) {
        var level = spellEntity.level;
        var blockPos = hitResult.getBlockPos();

        AABB areaOfEffect = new AABB(blockPos).inflate(3.0);
        explode(level, hitResult.getLocation(), areaOfEffect);
    }

    protected void explode(Level level, Vec3 pos, AABB areaOfEffect) {
        if (level.isClientSide) {
            playExplosionParticles(pos, (ServerLevel) level);
        }

        if (!level.isClientSide) {
            List<Entity> entities = level.getEntities(null, areaOfEffect);
            entities.forEach(entity -> entity.setRemainingFireTicks(20 * 10));
        }

        level.explode(null,
                pos.x, pos.y, pos.z,
                2.0f, true, Explosion.BlockInteraction.NONE);
    }

    protected void playExplosionParticles(Vec3 pos, ServerLevel level) {
        ParticleUtil.spawn(level, getParticle(), pos, 3, 5);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.DAMAGE;
    }
}

