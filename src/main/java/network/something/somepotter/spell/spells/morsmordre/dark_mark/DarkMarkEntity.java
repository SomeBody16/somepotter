package network.something.somepotter.spell.spells.morsmordre.dark_mark;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.EnumSet;

public class DarkMarkEntity extends FlyingMob implements IAnimatable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public static AttributeSupplier setAttributes() {
        return DarkMarkEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 100.0D)
                .build();
    }

    public DarkMarkEntity(EntityType<? extends DarkMarkEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        xpReward = 0;
        setPersistenceRequired();
        this.moveControl = new DarkMarkMoveControl(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(7, new DarkMarkLookGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(
                this, Player.class, 10,
                false, false,
                (entity) -> true
        ));
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isThundering() && !level.isRaining()) {
            discard();
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        var soundArea = new AABB(getOnPos()).inflate(100.0f);

        // Sound for nearby players
        if (level instanceof ServerLevel serverLevel) {
            level.players().stream()
                    .filter(player -> soundArea.contains(player.position()))
                    .forEach(player -> {
                        var pos = player.position();
                        serverLevel.playSound(
                                null, pos.x, pos.y, pos.z,
                                SoundEvents.WITHER_SPAWN, SoundSource.AMBIENT,
                                5.0f, 1.0f
                        );
                    });

            // Weather thunder
            serverLevel.setWeatherParameters(0, 6000, true, true);

            // Lightning at spawn
            var bolt = EntityType.LIGHTNING_BOLT.create(serverLevel);
            assert bolt != null;
            bolt.setPos(getEyePosition());
            bolt.setVisualOnly(true);
            level.addFreshEntity(bolt);
        }
        return super.finalizeSpawn(level, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public @NotNull SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.WITHER_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource pDamageSource) {
        return SoundEvents.WITHER_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WITHER_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 5.0f;
    }

    @Override
    public boolean shouldRender(double pX, double pY, double pZ) {
        return true;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double pDistance) {
        return true;
    }

    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    static class DarkMarkLookGoal extends Goal {
        private final DarkMarkEntity darkMark;

        public DarkMarkLookGoal(DarkMarkEntity darkMark) {
            this.darkMark = darkMark;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return true;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (this.darkMark.getTarget() == null) {
                Vec3 vec3 = this.darkMark.getDeltaMovement();
                //noinspection SuspiciousNameCombination
                this.darkMark.setYRot(-((float) Mth.atan2(vec3.x, vec3.z)) * (180F / (float) Math.PI));
                this.darkMark.yBodyRot = this.darkMark.getYRot();
                // set pitch angle to look straight ahead
                this.darkMark.setXRot(0);
            } else {
                LivingEntity livingentity = this.darkMark.getTarget();
                if (livingentity.distanceToSqr(this.darkMark) < 4096.0D) {
                    double d1 = livingentity.getX() - this.darkMark.getX();
                    double d2 = livingentity.getZ() - this.darkMark.getZ();
                    // calculate yaw angle
                    this.darkMark.setYRot(-((float) Mth.atan2(d1, d2)) * (180F / (float) Math.PI));
                    this.darkMark.yBodyRot = this.darkMark.getYRot();
                    // calculate pitch angle
                    double d3 = livingentity.getY() - this.darkMark.getY();
                    double d4 = Math.sqrt(d1 * d1 + d2 * d2);
                    this.darkMark.setXRot(-((float) Math.atan2(d3, d4)) * (180F / (float) Math.PI));
                }
            }

        }
    }

    static class DarkMarkMoveControl extends MoveControl {
        private final DarkMarkEntity darkMark;
        private int floatDuration;

        public DarkMarkMoveControl(DarkMarkEntity darkMark) {
            super(darkMark);
            this.darkMark = darkMark;
        }

        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                if (this.floatDuration-- <= 0) {
                    this.floatDuration += this.darkMark.getRandom().nextInt(5) + 2;
                    Vec3 vec3 = new Vec3(this.wantedX - this.darkMark.getX(), this.wantedY - this.darkMark.getY(), this.wantedZ - this.darkMark.getZ());
                    double d0 = vec3.length();
                    vec3 = vec3.normalize();
                    if (this.canReach(vec3, Mth.ceil(d0))) {
                        this.darkMark.setDeltaMovement(this.darkMark.getDeltaMovement().add(vec3.scale(0.1D)));
                    } else {
                        this.operation = MoveControl.Operation.WAIT;
                    }
                }

            }
        }

        private boolean canReach(Vec3 pPos, int pLength) {
            AABB aabb = this.darkMark.getBoundingBox();

            for (int i = 1; i < pLength; ++i) {
                aabb = aabb.move(pPos);
                if (!this.darkMark.level.noCollision(this.darkMark, aabb)) {
                    return false;
                }
            }

            return true;
        }
    }

}
