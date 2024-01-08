package network.something.somepotter.cast.projectile;

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
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.cast.touch.TouchCast;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.basic_cast.BasicCastSpell;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ProjectileCastEntity extends Projectile {

    public static final String ID = "spell_projectile_cast";
    public static EntityType<ProjectileCastEntity> TYPE;

    public static final EntityDataAccessor<String> SPELL_ID = SynchedEntityData.defineId(ProjectileCastEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Integer> ABILITY_POWER = SynchedEntityData.defineId(ProjectileCastEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Float> AREA_OF_EFFECT = SynchedEntityData.defineId(ProjectileCastEntity.class, EntityDataSerializers.FLOAT);


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
                                String spellId, int abilityPower, float areaOfEffect) {
        super(pEntityType, level);
        setOwner(caster);
        getEntityData().set(SPELL_ID, spellId);
        getEntityData().set(ABILITY_POWER, abilityPower);
        getEntityData().set(AREA_OF_EFFECT, areaOfEffect);
    }

    @Override
    protected void defineSynchedData() {
        getEntityData().define(SPELL_ID, BasicCastSpell.ID);
        getEntityData().define(ABILITY_POWER, 0);
        getEntityData().define(AREA_OF_EFFECT, 0.0F);
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
            var cancelled = SpellHitEvent.publish(getSpell(), getCaster(), level, getAbilityPower(), getAreaOfEffect(), hitResult);
            if (!cancelled) {
                TouchCast.playParticles(getSpell().getParticle(), level, hitResult.getLocation());
            }
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

    @SuppressWarnings("deprecation")
    @Override
    public void tick() {
        var entity = getOwner();
        if (level.isClientSide || (entity == null || !entity.isRemoved()) && level.hasChunkAt(blockPosition())) {
            super.tick();

            var hitResult = ProjectileUtil.getHitResult(this, this::canHitEntity);
            if (hitResult.getType() != HitResult.Type.MISS && ForgeEventFactory.onProjectileImpact(this, hitResult)) {
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

    protected void playTrailParticles() {
        var particle = getSpell().getParticle();

        var deltaX = getX() - xOld;
        var deltaY = getY() - yOld;
        var deltaZ = getZ() - zOld;
        var dist = Math.ceil(Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ));

        for (var i = 0; i < dist; i++) {
            var coeff = i / dist;
            level.addParticle(particle,
                    (float) (xo + deltaX + coeff),
                    (float) (yo + deltaY + coeff),
                    (float) (zo + deltaZ + coeff),
                    0.0125F * (level.random.nextFloat() - 0.5F),
                    0.0125F * (level.random.nextFloat() - 0.5F),
                    0.0125F * (level.random.nextFloat() - 0.5F)
            );
        }
    }
}
