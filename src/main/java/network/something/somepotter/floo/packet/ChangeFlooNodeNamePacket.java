package network.something.somepotter.floo.packet;

import ca.lukegrahamlandry.lib.network.ServerSideHandler;
import net.minecraft.server.level.ServerPlayer;
import network.something.somepotter.floo.network.FlooNetworkManager;
import network.something.somepotter.floo.network.FlooNode;

public class ChangeFlooNodeNamePacket implements ServerSideHandler {

    protected String name;
    protected FlooNode node;

    public ChangeFlooNodeNamePacket(FlooNode node, String name) {
        this.node = node;
        this.name = name;
    }

    @Override
    public void handle(ServerPlayer serverPlayer) {
        FlooNetworkManager.updateNodeName(node, name);
    }
}
