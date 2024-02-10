package network.something.somepotter.floo.packet;

import ca.lukegrahamlandry.lib.network.ServerSideHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.effect.Effects;
import network.something.somepotter.floo.network.FlooNetworkManager;
import network.something.somepotter.floo.network.FlooNode;

public class TeleportToNodePacket implements ServerSideHandler {

    protected Vec3 origin;
    protected FlooNode node;

    public TeleportToNodePacket(Vec3 origin, FlooNode node) {
        this.origin = origin;
        this.node = node;
    }

    @Override
    public void handle(ServerPlayer serverPlayer) {
        var level = serverPlayer.getLevel();

        if (!node.exists(serverPlayer.server)) {
            var nodeLevel = node.getLevel(serverPlayer.server);
            FlooNetworkManager.removeNode(nodeLevel, node.getPos());
            return;
        }

        var pos = Vec3.atCenterOf(node.getPos());
        serverPlayer.teleportTo(
                node.getLevel(serverPlayer.server),
                pos.x, node.y, pos.z,
                serverPlayer.getYRot(), serverPlayer.getXRot()
        );

        playTeleportSound(level, new BlockPos(origin));
        playTeleportSound(level, node.getPos());

        Effects.teleport(level, origin);
        Effects.teleport(level, Vec3.atCenterOf(node.getPos()));
    }

    protected void playTeleportSound(ServerLevel level, BlockPos pos) {
        var sound = SoundEvents.ENCHANTMENT_TABLE_USE;
        var pitch = level.random.nextFloat(0.1F, 1F);

        level.playSound(
                null,
                pos.getX(), pos.getY(), pos.getZ(),
                sound,
                SoundSource.BLOCKS,
                1.0F, pitch
        );
    }
}
