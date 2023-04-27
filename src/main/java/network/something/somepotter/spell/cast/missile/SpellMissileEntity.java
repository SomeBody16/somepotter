package network.something.somepotter.spell.cast.missile;

import net.minecraft.core.Direction;
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
import network.something.somepotter.SomePotter;
import network.something.somepotter.spell.api.particle.SpellParticle;
import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.spell.spells.Spells;
import network.something.somepotter.spell.spells.basic_cast.BasicCastSpell;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpellMissileEntity extends Projectile {

    private static final EntityDataAccessor<String> SPELL_ID = SynchedEntityData.defineId(SpellMissileEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> LIFETIME = SynchedEntityData.defineId(SpellMissileEntity.class, EntityDataSerializers.INT);

    public SpellMissileEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SpellMissileEntity(EntityType<? extends Projectile> pEntityType, Level pLevel, Entity caster, String spellId, int lifetime) {
        super(pEntityType, pLevel);
        setOwner(caster);
        this.getEntityData().set(SPELL_ID, spellId);
        this.getEntityData().set(LIFETIME, lifetime);
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(SPELL_ID, BasicCastSpell.instance.getId());
        this.getEntityData().define(LIFETIME, 0);
    }

    public String getSpellId() {
        return this.getEntityData().get(SPELL_ID);
    }

    public AbstractSpell getSpell() {
        return Spells.get(getSpellId());
    }

    public @Nullable LivingEntity getCaster() {
        return (LivingEntity) this.getOwner();
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        if (!this.level.isClientSide && getCaster() != null) {
            AbstractSpell.Event.onHitEntity(getSpell(), getCaster(), hitResult);
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        if (!this.level.isClientSide && getCaster() != null) {
            SomePotter.LOGGER.info("HIT BLOCK {}", hitResult.getBlockPos());
            AbstractSpell.Event.onHitBlock(getSpell(), getCaster(), hitResult);
        }
    }

    @Override
    protected void onHit(@NotNull HitResult hitResult) {
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
    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
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
            this.checkLifetime();

            Vec3 vec3 = this.getDeltaMovement();
            double d0 = this.getX() + vec3.x;
            double d1 = this.getY() + vec3.y;
            double d2 = this.getZ() + vec3.z;
            this.setPos(d0, d1, d2);

            playTrailParticles();
        } else {
            this.discard();
        }
    }

    protected void checkLifetime() {
        var lifeTime = this.getEntityData().get(LIFETIME) - 1;
        this.getEntityData().set(LIFETIME, lifeTime);
        if (--lifeTime <= 0) {
            var hitResult = new BlockHitResult(this.position(), Direction.UP, blockPosition(), true);
            this.onHit(hitResult);
        }
    }

    protected void playTrailParticles() {
        SpellParticle.playTrailParticles(getSpellId(), level, this);
    }


}
