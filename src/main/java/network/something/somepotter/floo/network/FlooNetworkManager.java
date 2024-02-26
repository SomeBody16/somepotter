package network.something.somepotter.floo.network;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class FlooNetworkManager {

    public static FlooNode addNode(ServerLevel level, BlockPos pos) {
        var existing = getNode(level, pos);
        if (existing != null) return existing;
        var name = "[%s %s %s]".formatted(pos.getX(), pos.getY(), pos.getZ());
        var node = new FlooNode(name, pos, level.dimension().location().toString());
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

    public static List<FlooNode> all() {
        return FlooNetworkData.get();
    }

    public static List<FlooNode> listFor(ServerPlayer player) {
        var playerName = player.getName().getString();
        var sortedNames = FlooSortData.get(player);

        var result = new ArrayList<>(FlooNetworkManager.all()
                .stream()
                // don't show nodes that the player doesn't have access to
                .filter(node -> node.allowedPlayers.isEmpty() || node.allowedPlayers.contains(playerName))
                // sort the nodes
                .sorted((node1, node2) -> {
                    var index1 = sortedNames.indexOf(node1.name);
                    var index2 = sortedNames.indexOf(node2.name);
                    if (index1 == -1) return 1;
                    if (index2 == -1) return -1;
                    return Integer.compare(index1, index2);
                })
                .toList());

        // Remove duplicate names
        for (int i = 0; i < result.size(); i++) {
            for (int j = i + 1; j < result.size(); j++) {
                if (result.get(i).name.equals(result.get(j).name)) {
                    result.remove(j);
                    j--;
                }
            }
        }

        return result;
    }

    public static List<FlooNode> getGroupOf(ServerPlayer player, FlooNode nodePredicate) {
        var playerName = player.getName().getString();

        return FlooNetworkManager.all()
                .stream()
                // don't show nodes that the player doesn't have access to
                .filter(node -> node.allowedPlayers.isEmpty() || node.allowedPlayers.contains(playerName))
                // show only with same name
                .filter(node -> node.name.equals(nodePredicate.name))
                .toList();
    }

    public static void removeNode(ServerLevel level, BlockPos pos) {
        FlooNetworkData.get().removeIf(node -> node.is(level, pos));
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

    public static boolean isAllowedFireplaceDimension(Level level) {
        return FlooNetworkConfig.get().allowedFireplaceDimensions.stream()
                .anyMatch(dim -> level.dimension().location().toString().matches(dim));
    }

    public static boolean isAllowedApparitionDimension(Level level) {
        return FlooNetworkConfig.get().allowedApparitionDimensions.stream()
                .anyMatch(dim -> level.dimension().location().toString().matches(dim));
    }

}
