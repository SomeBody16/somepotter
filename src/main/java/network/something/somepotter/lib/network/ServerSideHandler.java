package network.something.somepotter.lib.network;

import net.minecraft.server.level.ServerPlayer;

public interface ServerSideHandler {

    void handle(ServerPlayer player);

    default void sendToServer() {
        NetworkWrapper.sendToServer(this);
    }

}
