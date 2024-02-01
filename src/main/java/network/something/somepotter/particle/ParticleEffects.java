package network.something.somepotter.particle;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import network.something.somepotter.particle.packet.ChunkHighlightParticlePacket;
import network.something.somepotter.particle.packet.TeleportParticlePacket;
import network.something.somepotter.particle.packet.TouchParticlePacket;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.SpinParticleData;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
public class ParticleEffects {

    public static void chunkHighlight(Level level, ChunkPos chunkPos, int y, Color color) {
        chunkHighlight(level, chunkPos, y, color, color.darker());
    }

    public static void chunkHighlight(Level level, ChunkPos chunkPos, int y, Color startColor, Color endColor) {
        var pos = new BlockPos(chunkPos.getMiddleBlockX(), y, chunkPos.getMiddleBlockZ()).above();
        if (level instanceof ServerLevel serverLevel) {
            new ChunkHighlightParticlePacket(chunkPos, y, startColor, endColor).sendToTrackingClients(serverLevel, pos);
            return;
        }

        WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.02f, 0.6f, 0).setCoefficient(0.8f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                .setSpinData(SpinParticleData.create(0.1f).build())
                .setScaleData(GenericParticleData.create(0.4f, 0.3f, 0.1f).setCoefficient(0.7f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                .setColorData(ColorParticleData.create(startColor, endColor).build())
                .setLifetime(60)
                .enableNoClip()
                .setRandomOffset(10f, 2f)
                .setRandomMotion(0.001f, 0.001f)
                .repeat(level, pos.getX(), pos.getY(), pos.getZ(), 512);
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

        WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.02f, 0.2f, 0).setCoefficient(0.8f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                .setSpinData(SpinParticleData.create(0.1f).build())
                .setScaleData(GenericParticleData.create(0.4f, 0.3f, 0.1f).setCoefficient(0.7f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                .setColorData(ColorParticleData.create(startColor, endColor).build())
                .setLifetime(15)
                .enableNoClip()
                .setRandomOffset(2f, 2f)
                .setRandomMotion(0.001f, 0.001f)
                .repeat(level, pos.x, pos.y, pos.z, 64);
    }

    public static void trail(Level level, Vec3 pos, Color color) {
        trail(level, pos, color.darker().darker(), color);
    }

    public static void trail(Level level, Vec3 pos, Color startColor, Color endColor) {
        WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.12f, 0.16f, 0).setCoefficient(0.8f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                .setSpinData(SpinParticleData.create(0.1f).build())
                .setScaleData(GenericParticleData.create(0.3f, 0.1f, 0.05f).setCoefficient(0.7f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                .setColorData(ColorParticleData.create(startColor, endColor).build())
                .setLifetime(12)
                .enableNoClip()
                .setRandomOffset(0.1f, 0.1f)
                .setRandomMotion(0.01f, 0.01f)
                .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                .repeat(level, pos.x, pos.y, pos.z, 24);
    }

    public static void teleport(Level level, Vec3 pos) {
        teleport(level, pos, new Color(0x00AA00), new Color(0x00FF00));
    }

    public static void teleport(Level level, Vec3 pos, Color startColor, Color endColor) {
        if (level instanceof ServerLevel serverLevel) {
            new TeleportParticlePacket(pos, startColor, endColor).sendToTrackingClients(serverLevel, new BlockPos(pos));
            return;
        }

        WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.12f, 0.16f, 0).setCoefficient(0.8f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                .setSpinData(SpinParticleData.create(0.2f).build())
                .setScaleData(GenericParticleData.create(0.2f, 0.4f, 0.1f).setCoefficient(0.7f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                .setColorData(ColorParticleData.create(startColor, endColor).build())
                .setLifetime(25)
                .enableNoClip()
                .setRandomOffset(1f, 2f)
                .setRandomMotion(0.001f, 0.001f)
                .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                .repeat(level, pos.x, pos.y, pos.z, 32);

        WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.04f, 0.08f, 0).setCoefficient(0.8f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                .setSpinData(SpinParticleData.create(0.1f).build())
                .setScaleData(GenericParticleData.create(0.4f, 0.6f, 0.1f).setCoefficient(0.7f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                .setColorData(ColorParticleData.create(startColor, endColor).build())
                .setLifetime(35)
                .setRandomOffset(1f, 2f)
                .enableNoClip()
                .setRandomMotion(0.001f, 0.001f)
                .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                .repeat(level, pos.x, pos.y, pos.z, 32);
    }

}
