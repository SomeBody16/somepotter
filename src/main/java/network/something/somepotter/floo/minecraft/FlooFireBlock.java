package network.something.somepotter.floo.minecraft;

import iskallia.vault.init.ModConfigs;
import iskallia.vault.world.data.PlayerResearchesData;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
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
import network.something.somepotter.floo.network.FlooNetworkManager;
import network.something.somepotter.floo.packet.OpenFlooNetworkScreenPacket;
import network.something.somepotter.init.BlockInit;
import network.something.somepotter.integration.Integrations;
import network.something.somepotter.spells.cast.touch.TouchCast;
import network.something.somepotter.util.ColorUtil;

import java.util.List;

import static net.minecraft.Util.NIL_UUID;

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
                var particle = new ColorUtil(0xFFFF00).getParticle();
                TouchCast.playParticles(particle, serverLevel, new Vec3(pos.getX(), pos.getY(), pos.getZ()));
                return;
            }

            // Check for research
            if (Integrations.THE_VAULT.isLoaded()) {
                var allowedDims = List.of(Level.OVERWORLD, Level.NETHER, Level.END);
                if (!allowedDims.contains(serverLevel.dimension())) {
                    player.setSecondsOnFire(8);
                    return;
                }

                var researchData = PlayerResearchesData.get(serverLevel);
                var tree = researchData.getResearches(player);
                var research = ModConfigs.RESEARCHES.getByName("Floo Network");
                if (research == null || !tree.isResearched(research)) {
                    var name = new TextComponent("Floo Network");
                    name.setStyle(Style.EMPTY.withColor(-203978));
                    var msg = new TranslatableComponent("overlay.requires_research.interact_block", name);
                    player.sendMessage(msg, ChatType.GAME_INFO, NIL_UUID);
                    return;
                }
            }

            // Open GUI
            var nodes = FlooNetworkManager.all((ServerLevel) level);
            nodes = nodes.stream()
                    .filter(node -> !node.is((ServerLevel) level, pos))
                    .sorted((a, b) -> {
                        var aDist = FlooNetworkManager.playerDistanceTo(player, a.getPos());
                        var bDist = FlooNetworkManager.playerDistanceTo(player, b.getPos());
                        return Float.compare(aDist, bDist);
                    })
                    .toList();

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
}
