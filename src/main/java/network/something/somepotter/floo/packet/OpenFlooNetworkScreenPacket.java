package network.something.somepotter.floo.packet;

import ca.lukegrahamlandry.lib.network.ClientSideHandler;
import net.minecraft.client.Minecraft;
import network.something.somepotter.floo.client.DisableFlooNetworkScreen;
import network.something.somepotter.floo.minecraft.FlooNetworkScreen;
import network.something.somepotter.floo.network.FlooNode;

import java.util.List;

public class OpenFlooNetworkScreenPacket implements ClientSideHandler {


    protected FlooNode origin;
    protected List<FlooNode> nodes;

    public OpenFlooNetworkScreenPacket(FlooNode origin, List<FlooNode> nodes) {
        this.origin = origin;
        this.nodes = nodes;
    }

    @Override
    public void handle() {
        var mc = Minecraft.getInstance();
        if (mc.screen == null && !DisableFlooNetworkScreen.isDisabled()) {
            mc.setScreen(new FlooNetworkScreen(origin, nodes));
        }
    }
}
