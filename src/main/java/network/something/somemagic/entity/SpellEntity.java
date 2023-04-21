package network.something.somemagic.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import network.something.somemagic.magic.Spells;
import network.something.somemagic.magic.spell.core.ProjectileSpell;
import network.something.somemagic.magic.spell.core.UnknownProjectileSpell;
import network.something.somemagic.magic.spell.core.UnknownSpell;

public class SpellEntity extends Projectile {

    private static final EntityDataAccessor<String> DATA_SPELL = SynchedEntityData.defineId(SpellEntity.class, EntityDataSerializers.STRING);

    public SpellEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SpellEntity(EntityType<? extends Projectile> pEntityType, Level pLevel, Entity caster) {
        super(pEntityType, pLevel);
        setOwner(caster);
    }

    public void setSpell(String spell) {
        this.getEntityData().set(DATA_SPELL, spell);
    }

    public ProjectileSpell getSpell() {
        String spellName = this.getEntityData().get(DATA_SPELL);
        var spell = Spells.getSpell(spellName, (LivingEntity) getOwner());
        if (spell instanceof ProjectileSpell projectileSpell) {
            return projectileSpell;
        }
        return new UnknownProjectileSpell((LivingEntity) getOwner());
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_SPELL, UnknownSpell.ID);
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        if (!this.level.isClientSide) {
            getSpell().onHitEntity(this, hitResult);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        if (!this.level.isClientSide) {
            getSpell().onHitBlock(this, hitResult);
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level.isClientSide) {
            this.discard();
        }
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double pDistance) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void tick() {
        Entity entity = this.getOwner();
        if (this.level.isClientSide || (entity == null || !entity.isRemoved()) && this.level.hasChunkAt(this.blockPosition())) {
            super.tick();

            HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
            if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                this.onHit(hitresult);
            }

            this.checkInsideBlocks();
            Vec3 vec3 = this.getDeltaMovement();
            double d0 = this.getX() + vec3.x;
            double d1 = this.getY() + vec3.y;
            double d2 = this.getZ() + vec3.z;
            this.setPos(d0, d1, d2);

            getSpell().playTrailParticles(this);
        } else {
            this.discard();
        }
    }


}
