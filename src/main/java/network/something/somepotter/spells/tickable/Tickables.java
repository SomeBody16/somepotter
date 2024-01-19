package network.something.somepotter.spells.tickable;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.DEDICATED_SERVER)
public class Tickables {

    protected static final List<Tickable> TICKABLES = new ArrayList<>();

    public static void add(Tickable tickable) {
        TICKABLES.add(tickable);
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        TICKABLES.forEach(Tickable::tick);
        TICKABLES.removeIf(tickable -> {
            var isExpired = tickable.isExpired();
            if (isExpired) tickable.onExpired();
            return isExpired;
        });
    }

}
