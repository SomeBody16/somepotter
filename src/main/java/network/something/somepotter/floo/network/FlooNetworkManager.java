package network.something.somepotter.floo.network;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import network.something.somepotter.init.BlockInit;

import javax.annotation.Nullable;
import java.util.List;

public class FlooNetworkManager {

    public static FlooNode addNode(ServerLevel level, BlockPos pos) {
        var existing = getNode(level, pos);
        if (existing != null) return existing;
        var name = "[%s %s %s]".formatted(pos.getX(), pos.getY(), pos.getZ());
        var node = new FlooNode(name, pos, level.dimension().getRegistryName().toString());
        FlooNetworkData.get().add(node);
        FlooNetworkData.setDirty();
        return node;
    }

    public static void updateNodeName(FlooNode nodePredicate, String newName) {
        for (var node : FlooNetworkData.get()) {
            if (node.equals(nodePredicate)) {
                node.name = newName;
                FlooNetworkData.setDirty();
                return;
            }
        }
    }

    public static void addPrivateAccess(FlooNode nodePredicate, String name) {
        for (var node : FlooNetworkData.get()) {
            if (node.equals(nodePredicate)) {
                node.allowedPlayers.add(name);
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

    public static List<FlooNode> listFor(ServerLevel level, ServerPlayer player, @Nullable BlockPos pos) {
        var playerName = player.getName().getString();
        return FlooNetworkManager.all(level)
                .stream()
                // don't show the current node
                .filter(node -> !node.is(level, pos))
                // don't show nodes that the player doesn't have access to
                .filter(node -> node.allowedPlayers.isEmpty() || node.allowedPlayers.contains(playerName))
                // sort by distance
                .sorted((a, b) -> {
                    var aDist = FlooNetworkManager.playerDistanceTo(player, a.getPos());
                    var bDist = FlooNetworkManager.playerDistanceTo(player, b.getPos());
                    return Float.compare(aDist, bDist);
                })
                .toList();
    }

    public static void clean(ServerLevel level) {
        FlooNetworkData.get().removeIf(node -> {
            var dimension = level.dimension().getRegistryName().toString();
            if (!node.dimension.equals(dimension)) return false;

            var state = level.getBlockState(node.getPos());
            return !state.is(BlockInit.FLOO_FIRE.get());
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
