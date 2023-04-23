package network.something.somepotter.spell.tickable;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.spell.tickable.core.SpellTickable;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpellTickables {

    private static final List<SpellTickable> TICKABLES = new ArrayList<>();

    public static void addTickable(SpellTickable tickable) {
        TICKABLES.add(tickable);
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        TICKABLES.forEach(SpellTickable::tick);
        TICKABLES.removeIf(spellTickable -> {
            var isExpired = spellTickable.isExpired();
            if (isExpired) {
                spellTickable.onExpired();
            }
            return isExpired;
        });
    }

}
