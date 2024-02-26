package network.something.somepotter.spells.cast.projectile;

import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkHooks;
import network.something.somepotter.SomePotter;
import network.something.somepotter.effect.Effects;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.basic_cast.BasicCastSpell;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ProjectileCastEntity extends Projectile {

    public static final String ID = "spell_projectile_cast";
    public static EntityType<ProjectileCastEntity> TYPE;

    public static final EntityDataAccessor<String> SPELL_ID = SynchedEntityData.defineId(ProjectileCastEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Integer> ABILITY_POWER = SynchedEntityData.defineId(ProjectileCastEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Float> AREA_OF_EFFECT = SynchedEntityData.defineId(ProjectileCastEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Boolean> CAN_HIT_FLUID = SynchedEntityData.defineId(ProjectileCastEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<BlockPos> ORIGIN = SynchedEntityData.defineId(ProjectileCastEntity.class, EntityDataSerializers.BLOCK_POS);
    public static final EntityDataAccessor<Integer> RANGE = SynchedEntityData.defineId(ProjectileCastEntity.class, EntityDataSerializers.INT);


    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static void register(RegistryEvent.Register<EntityType<?>> event) {
        TYPE = (EntityType<ProjectileCastEntity>)
                EntityType.Builder.of(
                                (EntityType.EntityFactory<ProjectileCastEntity>) ProjectileCastEntity::new,
                                MobCategory.MISC
                        )
                        .fireImmune()
                        .sized(0.4F, 0.4F)
                        .build(ID)
                        .setRegistryName(SomePotter.MOD_ID, ID);

        event.getRegistry().register(TYPE);
    }

    public ProjectileCastEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ProjectileCastEntity(EntityType<? extends Projectile> pEntityType,
                                ServerLevel level, LivingEntity caster,
                                String spellId, int abilityPower, float areaOfEffect,
                                int range, boolean canHitFluid) {
        super(pEntityType, level);
        setOwner(caster);
        getEntityData().set(SPELL_ID, spellId);
        getEntityData().set(ABILITY_POWER, abilityPower);
        getEntityData().set(AREA_OF_EFFECT, areaOfEffect);
        getEntityData().set(ORIGIN, caster.blockPosition());
        getEntityData().set(RANGE, range);
        getEntityData().set(CAN_HIT_FLUID, canHitFluid);
    }

    @Override
    protected void defineSynchedData() {
        getEntityData().define(SPELL_ID, BasicCastSpell.ID);
        getEntityData().define(ABILITY_POWER, 0);
        getEntityData().define(AREA_OF_EFFECT, 0.0F);
        getEntityData().define(ORIGIN, BlockPos.ZERO);
        getEntityData().define(RANGE, 0);
        getEntityData().define(CAN_HIT_FLUID, false);
    }

    public LivingEntity getCaster() {
        return (LivingEntity) getOwner();
    }

    public Spell getSpell() {
        return SpellInit.get(getEntityData().get(SPELL_ID));
    }

    public int getAbilityPower() {
        return getEntityData().get(ABILITY_POWER);
    }

    public float getAreaOfEffect() {
        return getEntityData().get(AREA_OF_EFFECT);
    }

    public boolean canHitFluid() {
        return getEntityData().get(CAN_HIT_FLUID);
    }

    @Override
    protected void onHit(@NotNull HitResult hitResult) {
        super.onHit(hitResult);
        if (!level.isClientSide) {
            var level = (ServerLevel) this.level;
            SpellHitEvent.publish(getSpell(), getCaster(), level, getAbilityPower(), getAreaOfEffect(), hitResult);
            discard();
        }
    }


    @Override
    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        return false;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double pDistance) {
        return true;
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void tick() {
        var entity = getOwner();
        if (level.isClientSide || (entity == null || !entity.isRemoved()) && level.hasChunkAt(blockPosition())) {
            super.tick();
            tickRange();

            var hitResult = getHitResult(this, this::canHitEntity, canHitFluid());
            if (hitResult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitResult)) {
                if (hitResult instanceof EntityHitResult entityHitResult) {
                    Effects.touch(level, entityHitResult.getEntity().getEyePosition(), getSpell().getColor());
                }
                if (hitResult instanceof BlockHitResult blockHitResult) {
                    Effects.touch(level, blockHitResult.getBlockPos(), getSpell().getColor());
                }
                onHit(hitResult);
            }

            checkInsideBlocks();
            var vec3 = getDeltaMovement();
            var d0 = getX() + vec3.x;
            var d1 = getY() + vec3.y;
            var d2 = getZ() + vec3.z;
            setPos(d0, d1, d2);

            spawnTrailParticles();
        } else {
            discard();
        }
    }

    protected void tickRange() {
        var origin = getEntityData().get(ORIGIN);
        var range = getEntityData().get(RANGE);
        var position = new Vec3i(getX(), getY(), getZ());

        if (!origin.closerThan(position, range)) {
            var hitResult = new BlockHitResult(
                    position(),
                    getDirection(),
                    new BlockPos(position()),
                    true
            );
            onHit(hitResult);
            Effects.touch(level, position(), getSpell().getColor());
            discard();
        }
    }

    protected void spawnTrailParticles() {
        var color = getSpell().getColor();
        spawnTrailParticles(color);
        spawnTrailParticles(color.darker());
    }

    protected void spawnTrailParticles(Color color) {
        var rgb24 = color.getRGB();

        var r = color.getRed();
        var g = color.getGreen();
        var b = color.getBlue();

        var particleColorVec = new Vector3f(r / 255F, g / 255F, b / 255F);
        var particle = new DustParticleOptions(particleColorVec, 1F);

        double d0 = (double) (rgb24 >> 16 & 255) / 255.0D;
        double d1 = (double) (rgb24 >> 8 & 255) / 255.0D;
        double d2 = (double) (rgb24 >> 0 & 255) / 255.0D;

        for (int j = 0; j < 2; ++j) {
            level.addParticle(particle,
                    getRandomX(0.5D),
                    getRandomY(),
                    getRandomZ(0.5D),
                    d0, d1, d2
            );
        }
    }

    protected static HitResult getHitResult(Entity pProjectile, Predicate<Entity> pFilter, boolean canHitFluid) {
        Vec3 vec3 = pProjectile.getDeltaMovement();
        Level level = pProjectile.level;
        Vec3 vec31 = pProjectile.position();
        Vec3 vec32 = vec31.add(vec3);
        HitResult hitresult = level.clip(new ClipContext(vec31, vec32, ClipContext.Block.COLLIDER,
                canHitFluid ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE, pProjectile));
        if (hitresult.getType() != HitResult.Type.MISS) {
            vec32 = hitresult.getLocation();
        }

        HitResult hitresult1 = ProjectileUtil.getEntityHitResult(level, pProjectile, vec31, vec32, pProjectile.getBoundingBox().expandTowards(pProjectile.getDeltaMovement()).inflate(1.0D), pFilter);
        if (hitresult1 != null) {
            hitresult = hitresult1;
        }

        return hitresult;
    }
}
