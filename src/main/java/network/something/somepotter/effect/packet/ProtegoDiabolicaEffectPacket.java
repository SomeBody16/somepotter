package network.something.somepotter.effect.packet;

import ca.lukegrahamlandry.lib.network.ClientSideHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import network.something.somepotter.effect.Effects;

import java.awt.*;

public class ProtegoDiabolicaEffectPacket implements ClientSideHandler {

    protected Vec3 pos;
    protected int startColor;
    protected int endColor;

    public ProtegoDiabolicaEffectPacket(Vec3 pos, Color startColor, Color endColor) {
        this.pos = pos;
        this.startColor = startColor.getRGB();
        this.endColor = endColor.getRGB();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handle() {
        var level = Minecraft.getInstance().level;
        if (level == null) return;
        Effects.teleport(level, pos, new Color(startColor), new Color(endColor));
    }
}
