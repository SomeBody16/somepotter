package network.something.somepotter.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class SpellTouchEntity extends SpellProjectileEntity {

    public SpellTouchEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SpellTouchEntity(EntityType<? extends Projectile> pEntityType, Level pLevel, Entity caster, String spellId) {
        super(pEntityType, pLevel, caster, spellId);
    }

    @Override
    protected void playTrailParticles() {
    }
}
