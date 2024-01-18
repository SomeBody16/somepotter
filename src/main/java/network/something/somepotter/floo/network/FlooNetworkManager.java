package network.something.somepotter.floo.network;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import network.something.somepotter.SomePotter;
import network.something.somepotter.init.BlockInit;

import javax.annotation.Nullable;
import java.util.List;

public class FlooNetworkManager {

    public static void addNode(ServerLevel level, BlockPos pos) {
        if (getNode(level, pos) != null) return;
        var name = "[%s %s %s]".formatted(pos.getX(), pos.getY(), pos.getZ());
        var node = new FlooNode(name, pos, level.dimension().getRegistryName().toString());
        SomePotter.LOGGER.info("Adding node: " + node.name);
        FlooNetworkData.get().add(node);
        FlooNetworkData.setDirty();
    }

    public static void updateNodeName(FlooNode nodePredicate, String newName) {
        for (var node : FlooNetworkData.get()) {
            if (node.equals(nodePredicate)) {
                SomePotter.LOGGER.info("Updating node name: " + nodePredicate.name + " -> " + newName);
                node.name = newName;
                FlooNetworkData.setDirty();
                return;
            }
        }
    }

    @Nullable
    public static FlooNode getNode(ServerLevel level, BlockPos pos) {
        return FlooNetworkData.get().stream()
                .filter(node -> node.is(level, pos))
                .findFirst()
                .orElse(null);
    }

    public static List<FlooNode> all(ServerLevel level) {
        clean(level);
        return FlooNetworkData.get();
    }

    public static void clean(ServerLevel level) {
        FlooNetworkData.get().removeIf(node -> {
            var state = level.getBlockState(node.getPos());
            return state.getBlock() != BlockInit.FLOO_FIRE.get();
        });
        FlooNetworkData.setDirty();
    }

    public static float playerDistanceTo(Player player, BlockPos pos) {
        var playerPos = player.position();
        return (float) Math.sqrt(
                Math.pow(playerPos.x() - pos.getX(), 2) +
                        Math.pow(playerPos.y() - pos.getY(), 2) +
                        Math.pow(playerPos.z() - pos.getZ(), 2)
        );
    }

}
