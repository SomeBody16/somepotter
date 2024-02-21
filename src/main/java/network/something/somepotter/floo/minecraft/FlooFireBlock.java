package network.something.somepotter.floo.minecraft;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import network.something.somepotter.SomePotter;
import network.something.somepotter.effect.Effects;
import network.something.somepotter.floo.network.FlooNetworkManager;
import network.something.somepotter.floo.packet.OpenFlooNetworkScreenPacket;
import network.something.somepotter.init.BlockInit;
import network.something.somepotter.init.ItemInit;
import network.something.somepotter.integration.Integrations;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class FlooFireBlock extends Block {
    public static final String ID = "floo_fire";

    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(BlockInit.FLOO_FIRE.get(), RenderType.translucent());
    }

    public FlooFireBlock() {
        super(
                BlockBehaviour.Properties.of(Material.FIRE, MaterialColor.COLOR_GREEN)
                        .noCollission()
                        .instabreak()
                        .noDrops()
                        .lightLevel((pState) -> 15)
                        .sound(SoundType.WOOL)
        );
    }

    @SuppressWarnings("deprecation")
    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof ServerPlayer player
                && level instanceof ServerLevel serverLevel) {
            // Check if floo network exists
            var origin = FlooNetworkManager.getNode(serverLevel, pos);
            if (origin == null) {
                level.setBlockAndUpdate(pos, Blocks.FIRE.defaultBlockState());
                level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1, 1);
                Effects.teleport(serverLevel, Vec3.atCenterOf(pos));
                return;
            }

            // Check for research
            if (!Integrations.THE_VAULT.loadedAndHasResearch(serverLevel, player, "Floo Network")) {
                return;
            }

            // Open GUI
            var nodes = FlooNetworkManager.listFor(player);
            new OpenFlooNetworkScreenPacket(origin, nodes).sendToClient(player);
            return;
        }

        if (entity instanceof Player) return;
        if (!entity.fireImmune()) {
            entity.setRemainingFireTicks(entity.getRemainingFireTicks() + 1);
            if (entity.getRemainingFireTicks() == 0) {
                entity.setSecondsOnFire(8);
            }
            entity.hurt(DamageSource.IN_FIRE, 1);
        }
    }

    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel instanceof ServerLevel serverLevel) {
            var item = new ItemStack(ItemInit.FLOO_POWDER.get());
            Block.popResource(serverLevel, pPos, item);
        }
    }
}
