package network.something.somepotter.effect.client.light;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, value = Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class LightClientManager {

    static final List<LightEffect> EFFECTS = new ArrayList<>();

    public static void add(LightEffect effect) {
        EFFECTS.add(effect);
    }

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) return;
        EFFECTS.forEach(LightEffect::tick);
        EFFECTS.removeIf(effect -> {
            if (effect.ticks >= effect.duration) {
                effect.onExpire();
                return true;
            }
            return false;
        });
    }
}
