package network.something.somepotter.lib.network;

import net.minecraft.server.level.ServerPlayer;

public interface ClientSideHandler {

    void handle();

    default void sendToClient(ServerPlayer player) {
        NetworkWrapper.sendToClient(player, this);
    }

}
