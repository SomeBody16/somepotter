package network.something.somepotter.particle.packet;

import ca.lukegrahamlandry.lib.network.ClientSideHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.particle.ParticleEffects;

import java.awt.*;

public class TeleportParticlePacket implements ClientSideHandler {

    protected Vec3 pos;
    protected int startColor;
    protected int endColor;

    public TeleportParticlePacket(Vec3 pos, Color startColor, Color endColor) {
        this.pos = pos;
        this.startColor = startColor.getRGB();
        this.endColor = endColor.getRGB();
    }

    @Override
    public void handle() {
        var level = Minecraft.getInstance().level;
        if (level == null) return;
        ParticleEffects.teleport(level, pos, new Color(startColor), new Color(endColor));
    }
}
