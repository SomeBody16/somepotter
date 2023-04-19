package network.something.somemagic.spell;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import network.something.somemagic.entity.SpellEntity;

public abstract class TouchSpell extends ProjectileSpell {

    protected TouchSpell(String name, LivingEntity caster) {
        super(name, caster);
    }

    public double getCastRange() {
        return 16;
    }

    @Override
    public double getSpeed() {
        return 100.0F;
    }

    @Override
    public void onHitEntity(SpellEntity spellEntity, EntityHitResult hitResult) {
        playParticles(hitResult.getLocation());
    }

    @Override
    public void onHitBlock(SpellEntity spellEntity, BlockHitResult hitResult) {
        playParticles(hitResult.getLocation());
    }

    public void playParticles(Vec3 pos) {
        ServerLevel level = (ServerLevel) caster.level;
        level.sendParticles(
                getColor().getParticle(),
                pos.x, pos.y, pos.z,
                25,
                1, 1, 1,
                0.01
        );
    }

    @Override
    public void playTrailParticles(SpellEntity spellEntity) {
    }
}
