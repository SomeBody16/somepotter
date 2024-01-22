package network.something.somelib.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import network.something.somelib.serialization.GenericHolder;
import network.something.somepotter.SomePotter;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class NetworkPacketToClient {

    public final GenericHolder<? extends ClientSideHandler> holder;

    public NetworkPacketToClient(GenericHolder<? extends ClientSideHandler> holder) {
        this.holder = holder;
    }

    public NetworkPacketToClient(FriendlyByteBuf buffer) {
        this.holder = GenericHolder.decodeBytes(buffer);
    }

    public void encode(FriendlyByteBuf buffer) {
        holder.encodeBytes(buffer);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        final var success = new AtomicBoolean(false);

        ctx.get().enqueueWork(() -> {
            try {
                holder.get().handle();

                ctx.get().setPacketHandled(true);
                success.set(true);
            } catch (Exception e) {
                SomePotter.LOGGER.error("Error while handling packet on client", e);
            }
        });

        return success.get();
    }
}
