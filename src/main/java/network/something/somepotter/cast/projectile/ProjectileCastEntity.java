package network.something.somepotter.cast.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkHooks;
import network.something.somepotter.SomePotter;
import network.something.somepotter.cast.touch.TouchCast;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.basic_cast.BasicCastSpell;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ProjectileCastEntity extends Projectile {

    public static final String ID = "spell_projectile_cast";
    public static EntityType<ProjectileCastEntity> TYPE;

    public static final EntityDataAccessor<String> SPELL_ID = SynchedEntityData.defineId(ProjectileCastEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Integer> ABILITY_POWER = SynchedEntityData.defineId(ProjectileCastEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Float> AREA_OF_EFFECT = SynchedEntityData.defineId(ProjectileCastEntity.class, EntityDataSerializers.FLOAT);
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
                        .sized(0.25F, 0.25F)
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
                                int range) {
        super(pEntityType, level);
        setOwner(caster);
        getEntityData().set(SPELL_ID, spellId);
        getEntityData().set(ABILITY_POWER, abilityPower);
        getEntityData().set(AREA_OF_EFFECT, areaOfEffect);
        getEntityData().set(ORIGIN, caster.blockPosition());
        getEntityData().set(RANGE, range);
    }

    @Override
    protected void defineSynchedData() {
        getEntityData().define(SPELL_ID, BasicCastSpell.ID);
        getEntityData().define(ABILITY_POWER, 0);
        getEntityData().define(AREA_OF_EFFECT, 0.0F);
        getEntityData().define(ORIGIN, BlockPos.ZERO);
        getEntityData().define(RANGE, 0);
    }

    public LivingEntity getCaster() {
        return (LivingEntity) getOwner();
    }

    public Spell getSpell() {
        return SpellInit.getSpell(getEntityData().get(SPELL_ID));
    }

    public int getAbilityPower() {
        return getEntityData().get(ABILITY_POWER);
    }

    public float getAreaOfEffect() {
        return getEntityData().get(AREA_OF_EFFECT);
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
    public void remove(RemovalReason pReason) {
        if (level instanceof ServerLevel serverLevel) {
            TouchCast.playParticles(getSpell().getParticle(), serverLevel, position());
        }
        super.remove(pReason);
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

            var hitResult = ProjectileUtil.getHitResult(this, this::canHitEntity);
            if (hitResult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitResult)) {
                onHit(hitResult);
            }

            checkInsideBlocks();
            var vec3 = getDeltaMovement();
            var d0 = getX() + vec3.x;
            var d1 = getY() + vec3.y;
            var d2 = getZ() + vec3.z;
            setPos(d0, d1, d2);

            playTrailParticles();
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
            discard();
        }
    }

    protected void playTrailParticles() {
        var color = getSpell().getColor();
        var rgb24 = color.getRGB24();
        var particle = color.getParticle();

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
}
