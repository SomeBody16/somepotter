package network.something.somelib.network;

import net.minecraft.server.level.ServerPlayer;

public interface ClientSideHandler {

    void handle();

    default void sendToClient(ServerPlayer player) {
        NetworkWrapper.sendToClient(player, this);
    }

}
