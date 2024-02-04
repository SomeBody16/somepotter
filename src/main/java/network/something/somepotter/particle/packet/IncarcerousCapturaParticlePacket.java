package network.something.somepotter.particle.packet;

import ca.lukegrahamlandry.lib.network.ClientSideHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import network.something.somepotter.particle.ParticleEffects;

import java.awt.*;

public class IncarcerousCapturaParticlePacket implements ClientSideHandler {

    protected Vec3 caster, target;
    protected int color;

    public IncarcerousCapturaParticlePacket(Vec3 caster, Vec3 target, Color color) {
        this.caster = caster;
        this.target = target;
        this.color = color.getRGB();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handle() {
        var level = Minecraft.getInstance().level;
        if (level == null) return;
        ParticleEffects.incarcerousCaptura(level, caster, target, new Color(color));
    }
}
