package network.something.somepotter.particle.packet;

import ca.lukegrahamlandry.lib.network.ClientSideHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.ChunkPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import network.something.somepotter.particle.ParticleEffects;

import java.awt.*;

public class ChunkHighlightParticlePacket implements ClientSideHandler {

    protected ChunkPos pos;
    protected int y;
    protected int startColor;
    protected int endColor;

    public ChunkHighlightParticlePacket(ChunkPos pos, int y, Color startColor, Color endColor) {
        this.pos = pos;
        this.y = y;
        this.startColor = startColor.getRGB();
        this.endColor = endColor.getRGB();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handle() {
        var level = Minecraft.getInstance().level;
        if (level == null) return;
        ParticleEffects.chunkHighlight(level, pos, y, new Color(startColor), new Color(endColor));
    }
}
