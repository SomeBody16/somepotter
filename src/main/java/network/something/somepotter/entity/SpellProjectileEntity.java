package network.something.somepotter.entity;

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
import net.minecraftforge.common.MinecraftForge;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.particle.SpellParticle;
import network.something.somepotter.spell.spells.basic_cast.BasicCastSpell;
import org.jetbrains.annotations.NotNull;

public class SpellProjectileEntity extends Projectile {

    private static final EntityDataAccessor<String> SPELL_ID = SynchedEntityData.defineId(SpellProjectileEntity.class, EntityDataSerializers.STRING);

    public SpellProjectileEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SpellProjectileEntity(EntityType<? extends Projectile> pEntityType, Level pLevel, Entity caster, String spellId) {
        super(pEntityType, pLevel);
        setOwner(caster);
        this.getEntityData().set(SPELL_ID, spellId);
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(SPELL_ID, BasicCastSpell.instance.getId());
    }

    public String getSpellId() {
        return this.getEntityData().get(SPELL_ID);
    }

    public LivingEntity getCaster() {
        return (LivingEntity) this.getOwner();
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        if (!this.level.isClientSide) {
            var event = new SpellHitEntityEvent(getSpellId(), getCaster(), hitResult);
            MinecraftForge.EVENT_BUS.post(event);
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        if (!this.level.isClientSide) {
            var event = new SpellHitBlockEvent(getSpellId(), getCaster(), hitResult);
            MinecraftForge.EVENT_BUS.post(event);
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

    protected void playTrailParticles() {
        SpellParticle.playTrailParticles(getSpellId(), level, this);
    }


}
