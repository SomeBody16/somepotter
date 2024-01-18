package network.something.somepotter.floo.packet;

import ca.lukegrahamlandry.lib.network.ServerSideHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.floo.network.FlooNode;
import network.something.somepotter.spells.cast.touch.TouchCast;
import network.something.somepotter.util.ColorUtil;

public class TeleportToNodePacket implements ServerSideHandler {

    protected FlooNode node;

    public TeleportToNodePacket(FlooNode node) {
        this.node = node;
    }

    @Override
    public void handle(ServerPlayer serverPlayer) {
        var level = serverPlayer.getLevel();
        var particle = new ColorUtil(0x00AA00).getParticle();

        playTeleportSound(level, serverPlayer.blockPosition());
        TouchCast.playParticles(particle, level, serverPlayer.getEyePosition());

        var pos = Vec3.atCenterOf(node.getPos());
        serverPlayer.teleportTo(
                node.getLevel(serverPlayer.server),
                pos.x, node.y, pos.z,
                serverPlayer.getYRot(), serverPlayer.getXRot()
        );

        playTeleportSound(level, node.getPos());
        TouchCast.playParticles(particle, level, new Vec3(node.x, node.y, node.z));
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
