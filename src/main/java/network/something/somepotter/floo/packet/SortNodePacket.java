package network.something.somepotter.floo.packet;

import ca.lukegrahamlandry.lib.network.ServerSideHandler;
import net.minecraft.server.level.ServerPlayer;
import network.something.somepotter.floo.network.FlooNode;
import network.something.somepotter.floo.network.FlooSortData;

import java.util.List;

public class SortNodePacket implements ServerSideHandler {

    protected List<FlooNode> nodes;

    public SortNodePacket(List<FlooNode> nodes) {
        this.nodes = nodes;
    }

    @Override
    public void handle(ServerPlayer serverPlayer) {
        var sortedNames = FlooSortData.get(serverPlayer);
        sortedNames.clear();
        for (var node : nodes) {
            sortedNames.add(node.name);
        }
        FlooSortData.setDirty(serverPlayer);
    }
}
