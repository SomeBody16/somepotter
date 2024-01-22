package network.something.somelib.network;

import net.minecraft.server.level.ServerPlayer;

public interface ServerSideHandler {

    void handle(ServerPlayer player);

    default void sendToServer() {
        NetworkWrapper.sendToServer(this);
    }

}
