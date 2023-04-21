package network.something.somepotter.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import network.something.somepotter.SomePotter;
import network.something.somepotter.packet.PacketCastSpell;

public class MessageInit {

    private static SimpleChannel CHANNEL;

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        CHANNEL = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(SomePotter.MOD_ID, "spell"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        CHANNEL.messageBuilder(PacketCastSpell.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PacketCastSpell::new)
                .encoder(PacketCastSpell::toBytes)
                .consumer(PacketCastSpell::handle)
                .add();
    }

    public static <TMessage> void sendToServer(TMessage message) {
        CHANNEL.sendToServer(message);
    }

    public static <TMessage> void sendToPlayer(TMessage message, ServerPlayer player) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

}
