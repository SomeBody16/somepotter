package network.something.somepotter.magic.spell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.entity.SpellEntity;
import network.something.somepotter.magic.spell.core.ProjectileSpell;
import network.something.somepotter.util.SpellColor;

import javax.annotation.Nullable;

public class AguamentiSpell extends ProjectileSpell {
    public static final String ID = "aguamenti";

    public AguamentiSpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public int getCooldownTicks() {
        return 15;
    }

    @Override
    public void onHitEntity(SpellEntity spellEntity, EntityHitResult hitResult) {
        if (hitResult.getEntity().isOnFire()) {
            hitResult.getEntity().clearFire();
        }
    }

    @Override
    public void onHitBlock(SpellEntity spellEntity, BlockHitResult hitResult) {
        var blockPos = hitResult.getBlockPos();
        var direction = hitResult.getDirection();
        var targetBlockPos = blockPos.relative(direction);

        var blockState = spellEntity.level.getBlockState(blockPos);
        targetBlockPos = canBlockContainFluid(spellEntity.level, blockPos, blockState)
                ? blockPos
                : targetBlockPos;

        placeFluid(spellEntity.level, targetBlockPos, hitResult);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.UTILITY;
    }

    private boolean canBlockContainFluid(Level worldIn, BlockPos posIn, BlockState blockstate) {
        return blockstate.getBlock() instanceof LiquidBlockContainer && ((LiquidBlockContainer) blockstate.getBlock()).canPlaceLiquid(worldIn, posIn, blockstate, Fluids.WATER);
    }

    private boolean placeFluid(Level level, BlockPos blockPos, @Nullable BlockHitResult hitResult) {
        BlockState blockstate = level.getBlockState(blockPos);
        Block block = blockstate.getBlock();
        Material material = blockstate.getMaterial();
        boolean canBeReplacedWithWater = blockstate.canBeReplaced(Fluids.WATER);
        boolean flag1 = blockstate.isAir()
                || canBeReplacedWithWater
                || block instanceof LiquidBlockContainer
                && ((LiquidBlockContainer) block).canPlaceLiquid(level, blockPos, blockstate, Fluids.WATER);
        if (!flag1) {
            return hitResult != null && placeFluid(level, hitResult.getBlockPos().relative(hitResult.getDirection()), null);
        } else if (level.dimensionType().ultraWarm()) {
            int i = blockPos.getX();
            int j = blockPos.getY();
            int k = blockPos.getZ();
            level.playSound(caster instanceof Player player ? player : null, blockPos,
                    SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS,
                    0.5F, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F);

            for (int l = 0; l < 8; ++l) {
                level.addParticle(ParticleTypes.LARGE_SMOKE, (double) i + Math.random(), (double) j + Math.random(), (double) k + Math.random(), 0.0D, 0.0D, 0.0D);
            }

            return true;
        } else if (block instanceof LiquidBlockContainer && ((LiquidBlockContainer) block).canPlaceLiquid(level, blockPos, blockstate, Fluids.WATER)) {
            ((LiquidBlockContainer) block).placeLiquid(level, blockPos, blockstate, (Fluids.WATER).getSource(false));
            this.playEmptySound(level, blockPos);
            return true;
        } else {
            if (!level.isClientSide && canBeReplacedWithWater && !material.isLiquid()) {
                level.destroyBlock(blockPos, true);
            }

            if (!level.setBlock(blockPos, Fluids.WATER.defaultFluidState().createLegacyBlock(), 11) && !blockstate.getFluidState().isSource()) {
                return false;
            } else {
                this.playEmptySound(level, blockPos);
                return true;
            }
        }
    }

    protected void playEmptySound(LevelAccessor pLevel, BlockPos pPos) {
        SoundEvent soundevent = Fluids.WATER.getAttributes().getEmptySound();
        if (soundevent == null) soundevent = SoundEvents.BUCKET_EMPTY;
        pLevel.playSound(caster instanceof Player player ? player : null, pPos, soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
        pLevel.gameEvent(caster instanceof Player player ? player : null, GameEvent.FLUID_PLACE, pPos);
    }
}

