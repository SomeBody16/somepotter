package network.something.somepotter.particle;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.particle.packet.ChunkHighlightParticlePacket;
import network.something.somepotter.particle.packet.TeleportParticlePacket;
import network.something.somepotter.particle.packet.TouchParticlePacket;

import java.awt.*;

public class ParticleEffects {

    public static void chunkHighlight(Level level, ChunkPos chunkPos, int y, Color color) {
        chunkHighlight(level, chunkPos, y, color, color.darker());
    }

    public static void chunkHighlight(Level level, ChunkPos chunkPos, int y, Color startColor, Color endColor) {
        var pos = new BlockPos(chunkPos.getMiddleBlockX(), y, chunkPos.getMiddleBlockZ());
        if (level instanceof ServerLevel serverLevel) {
            new ChunkHighlightParticlePacket(chunkPos, y, startColor, endColor).sendToTrackingClients(serverLevel, pos);
            return;
        }
        ParticleEffectsClient.chunkHighlight(level, pos, startColor, endColor);
    }

    public static void touch(Level level, Vec3 pos, Color color) {
        touch(level, pos, color, color.darker());
    }

    public static void touch(Level level, Vec3 pos, Color startColor, Color endColor) {
        var blockPos = new BlockPos(pos);
        if (level instanceof ServerLevel serverLevel) {
            new TouchParticlePacket(pos, startColor, endColor).sendToTrackingClients(serverLevel, blockPos);
            return;
        }
        ParticleEffectsClient.touch(level, pos, startColor, endColor);
    }

    public static void teleport(Level level, Vec3 pos) {
        teleport(level, pos, new Color(0x00AA00), new Color(0x00FF00));
    }

    public static void teleport(Level level, Vec3 pos, Color startColor, Color endColor) {
        if (level instanceof ServerLevel serverLevel) {
            new TeleportParticlePacket(pos, startColor, endColor).sendToTrackingClients(serverLevel, new BlockPos(pos));
            return;
        }
        ParticleEffectsClient.teleport(level, pos, startColor, endColor);
    }

}
