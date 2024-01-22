package network.something.somelib.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import network.something.somelib.serialization.GenericHolder;
import network.something.somepotter.SomePotter;

public class NetworkWrapper {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(SomePotter.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void init() {
        int id = 0;

        CHANNEL.messageBuilder(NetworkPacketToServer.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(NetworkPacketToServer::encode);
        CHANNEL.messageBuilder(NetworkPacketToClient.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(NetworkPacketToClient::encode);
    }

    public static <T extends ServerSideHandler> void sendToServer(T message) {
        var holder = new GenericHolder<>(message);
        var packet = new NetworkPacketToServer(holder);
        CHANNEL.sendToServer(packet);
    }

    public static <T extends ClientSideHandler> void sendToClient(ServerPlayer player, T message) {
        var holder = new GenericHolder<>(message);
        var packet = new NetworkPacketToClient(holder);
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

}
