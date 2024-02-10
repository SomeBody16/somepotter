package network.something.somepotter.effect.client;

import com.mojang.math.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.effect.client.light.ExplosionLightEffect;
import network.something.somepotter.effect.client.light.LightClientManager;

import java.awt.*;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, value = Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class LightClient {

    public static void explosion(int duration, Vector3f pos, Color color, int radius) {
        var effect = new ExplosionLightEffect(duration, pos, color.getRGB(), radius, false);
        LightClientManager.add(effect);
    }

}
