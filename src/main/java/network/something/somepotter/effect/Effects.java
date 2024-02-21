package network.something.somepotter.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.effect.packet.*;

import java.awt.*;

public class Effects {

    public static void protegoDiabolica(Level level, Vec3 pos, Color color) {
        protegoDiabolica(level, pos, color, color.darker());
    }

    public static void protegoDiabolica(Level level, Vec3 pos, Color startColor, Color endColor) {
        if (level instanceof ServerLevel serverLevel) {
            new ProtegoDiabolicaEffectPacket(pos, startColor, endColor)
                    .sendToTrackingClients(serverLevel, new BlockPos(pos));
            return;
        }
        EffectsClient.protegoDiabolica(level, pos, startColor, endColor);
    }

    public static void incarcerousCaptura(Level level, Vec3 caster, Vec3 target, Color color) {
        if (level instanceof ServerLevel serverLevel) {
            new IncarcerousCapturaEffectPacket(caster, target, color)
                    .sendToTrackingClients(serverLevel, new BlockPos(target));
            return;
        }
        EffectsClient.incarcerousCaptura(level, caster, target, color);
    }

    public static void chunkHighlight(Level level, ChunkPos chunkPos, int y, Color color) {
        chunkHighlight(level, chunkPos, y, color, color.darker());
    }

    public static void chunkHighlight(Level level, ChunkPos chunkPos, int y, Color startColor, Color endColor) {
        var pos = new BlockPos(chunkPos.getMiddleBlockX(), y, chunkPos.getMiddleBlockZ());
        if (level instanceof ServerLevel serverLevel) {
            new ChunkHighlightEffectPacket(chunkPos, y, startColor, endColor).sendToTrackingClients(serverLevel, pos);
            return;
        }
        EffectsClient.chunkHighlight(level, pos, startColor, endColor);
    }

    public static void touch(Level level, BlockPos pos, Color color) {
        touch(level, Vec3.atCenterOf(pos), color, color.darker());
    }

    public static void touch(Level level, Vec3 pos, Color color) {
        touch(level, pos, color, color.darker());
    }

    public static void touch(Level level, Vec3 pos, Color startColor, Color endColor) {
        var blockPos = new BlockPos(pos);
        if (level instanceof ServerLevel serverLevel) {
            new TouchEffectPacket(pos, startColor, endColor).sendToTrackingClients(serverLevel, blockPos);
            return;
        }
        EffectsClient.touch(level, pos, startColor, endColor);
    }

    public static void teleport(Level level, Vec3 pos) {
        teleport(level, pos, new Color(0x00AA00), new Color(0x00FF00));
    }

    public static void teleport(Level level, Vec3 pos, Color startColor, Color endColor) {
        if (level instanceof ServerLevel serverLevel) {
            new TeleportEffectPacket(pos, startColor, endColor).sendToTrackingClients(serverLevel, new BlockPos(pos));
            return;
        }
        EffectsClient.teleport(level, pos, startColor, endColor);
    }

}
