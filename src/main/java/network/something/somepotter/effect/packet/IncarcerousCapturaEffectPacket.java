package network.something.somepotter.effect.packet;

import ca.lukegrahamlandry.lib.network.ClientSideHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import network.something.somepotter.effect.Effects;

import java.awt.*;

public class IncarcerousCapturaEffectPacket implements ClientSideHandler {

    protected Vec3 caster, target;
    protected int color;

    public IncarcerousCapturaEffectPacket(Vec3 caster, Vec3 target, Color color) {
        this.caster = caster;
        this.target = target;
        this.color = color.getRGB();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handle() {
        var level = Minecraft.getInstance().level;
        if (level == null) return;
        Effects.incarcerousCaptura(level, caster, target, new Color(color));
    }
}
