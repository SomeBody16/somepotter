package network.something.somepotter.spells.spell.protego_diabolica;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import network.something.somepotter.effect.Effects;
import network.something.somepotter.init.BlockInit;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.spells.cast.projectile.ProjectileCastEntity;
import network.something.somepotter.spells.spell.protego_maxima.claim.ClaimManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Random;
import java.util.Set;

public class ProtegoDiabolicaBlock extends Block {

    public static final BooleanProperty BLOCKING = BooleanProperty.create("blocking");

    public static final VoxelShape COLLISION_SHAPE = Shapes.box(0.002, 0.002, 0.002, 0.998, 0.998, 0.998);

    public static BlockBehaviour.Properties getProperties() {
        return BlockBehaviour.Properties.of(Material.GLASS)
                .noOcclusion()
                .isRedstoneConductor((pState, pLevel, pPos) -> false)
                .strength(-1F, 3600000.8F)
                .isValidSpawn((pState, pLevel, pPos, pType) -> false)
                .lightLevel((pState) -> 8)
                .noDrops();
    }

    public ProtegoDiabolicaBlock() {
        super(getProperties());
        registerDefaultState(
                defaultBlockState()
                        .setValue(BLOCKING, true)
        );
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 1F;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(BLOCKING);
    }

    @Override
    public boolean canEntityDestroy(BlockState state, BlockGetter level, BlockPos pos, Entity entity) {
        return false;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState pState) {
        return PushReaction.BLOCK;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return true;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pLevel instanceof ServerLevel serverLevel
                && !ClaimManager.exists(serverLevel, pPos)) {
            removeShield(serverLevel, pPos);
            return Shapes.empty();
        }

        if (pContext instanceof EntityCollisionContext entityContext) {
            var entity = entityContext.getEntity();

            // Magic
            if (entity instanceof ProjectileCastEntity) {
                return COLLISION_SHAPE;
            }

            // Ender pearl and stuff
            if (entity instanceof ThrowableProjectile) {
                return COLLISION_SHAPE;
            }

            // Item collision
            if (isItem(entity)) {
                return Shapes.empty();
            }

            if (pState.getValue(BLOCKING)) {
                if (entity instanceof Player player
                        && pLevel instanceof Level level
                        && ClaimManager.hasAccess(level, player, pPos)) {
                    return Shapes.empty();
                }

                return COLLISION_SHAPE;
            }

        }
        return Shapes.empty();
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRandom) {
        var pos = Vec3.atCenterOf(pPos);
        var color = getColor(pState);
        Effects.protegoDiabolica(pLevel, pos, color);
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, @NotNull Entity entity) {
        if (entity instanceof ItemEntity itemEntity) {
            var stack = itemEntity.getItem();
            // TODO: Change color of particles
//
//            if (item.is(Items.STICK)) {
//                itemEntity.remove(Entity.RemovalReason.KILLED);
//                pState.setValue(BLOCK_ITEMS, !pState.getValue(BLOCK_ITEMS));
//                updateShield(pLevel, pPos, pState);
//                return;
//            }
//
//            if (item.is(Blocks.WHITE_WOOL.asItem())) {
//                itemEntity.remove(Entity.RemovalReason.KILLED);
//                pState.setValue(BLOCK_PASSIVE, !pState.getValue(BLOCK_PASSIVE));
//                updateShield(pLevel, pPos, pState);
//                return;
//            }
//
//            if (item.is(Items.GUNPOWDER.asItem())) {
//                itemEntity.remove(Entity.RemovalReason.KILLED);
//                pState.setValue(BLOCK_HOSTILE, !pState.getValue(BLOCK_HOSTILE));
//                updateShield(pLevel, pPos, pState);
//                return;
//            }
//
//            if (item.is(Items.NETHERITE_SWORD)) {
//                itemEntity.remove(Entity.RemovalReason.KILLED);
//                pState.setValue(DEAL_DAMAGE, !pState.getValue(DEAL_DAMAGE));
//                updateShield(pLevel, pPos, pState);
//                return;
//            }

        }

        if (isItem(entity)) return;
        if (!pState.getValue(BLOCKING)) return;

        if (pLevel.getGameTime() % 10 != 0) return;
        if (entity instanceof ServerPlayer player
                && ClaimManager.hasAccess(pLevel, player, pPos)) return;

        var spell = (ProtegoDiabolicaSpell) SpellInit.get(ProtegoDiabolicaSpell.ID);
        var source = spell.getDamageSource();
        var damage = 2;

        entity.hurt(source, damage);
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (!pLevel.isClientSide) {
            var isBlockingPlayers = !isPowered(pLevel, pPos);

            getShieldPositions(pLevel, pPos).forEach(pos -> {
                var posBlockingPlayers = pLevel.getBlockState(pos).getValue(BLOCKING);
                if (posBlockingPlayers != isBlockingPlayers) {
                    pLevel.setBlockAndUpdate(pos, pLevel.getBlockState(pos).setValue(BLOCKING, isBlockingPlayers));
                    playEffect(pLevel, pos);
                }
            });
        }
    }

    public static boolean isPowered(Level level, BlockPos pos) {
        return getShieldPositions(level, pos).stream().anyMatch(level::hasNeighborSignal);
    }

    public static void updateShield(Level level, BlockPos pos, BlockState newState) {
        getShieldPositions(level, pos).forEach(blockPos -> {
            level.setBlock(blockPos, newState, 2);
            playEffect(level, blockPos);
        });
    }

    public static Set<BlockPos> getShieldPositions(Level level, BlockPos pos) {
        return ProtegoDiabolicaListener.listBlocksOfType(level, pos, 64, BlockInit.PROTEGO_DIABOLICA.get());
    }

    public static void removeShield(Level level, BlockPos shieldPos) {
        getShieldPositions(level, shieldPos).forEach(pos ->
                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState()));
    }

    public static boolean isItem(Entity entity) {
        return !(entity instanceof LivingEntity);
    }

    public static void playEffect(Level level, BlockPos pos) {
//        var color = getColor(level.getBlockState(pos));
//        ParticleEffects.protegoDiabolica(level, Vec3.atCenterOf(pos), color);
    }

    public static Color getColor(BlockState state) {
        var defaultColor = SpellInit.get(ProtegoDiabolicaSpell.ID).getColor();
        var unlockedColor = Color.GREEN;
        return state.getValue(BLOCKING) ? defaultColor : unlockedColor;
    }
}
