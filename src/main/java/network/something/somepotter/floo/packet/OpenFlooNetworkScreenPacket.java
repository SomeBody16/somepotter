package network.something.somepotter.floo.packet;

import ca.lukegrahamlandry.lib.network.ClientSideHandler;
import network.something.somepotter.floo.client.FlooNetworkScreen;
import network.something.somepotter.floo.network.FlooNode;

import javax.annotation.Nullable;
import java.util.List;

public class OpenFlooNetworkScreenPacket implements ClientSideHandler {

    @Nullable
    protected FlooNode origin;
    protected List<FlooNode> nodes;

    public OpenFlooNetworkScreenPacket(@Nullable FlooNode origin, List<FlooNode> nodes) {
        this.origin = origin;
        this.nodes = nodes;
    }

    @Override
    public void handle() {
        new FlooNetworkScreen(origin, nodes).tryOpen();
    }
}
