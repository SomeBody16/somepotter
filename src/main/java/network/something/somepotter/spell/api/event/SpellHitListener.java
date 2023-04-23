package network.something.somepotter.spell.api.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SomePotter.MOD_ID)
public class SpellHitListener {

    private static final Map<String, List<SpellHitListener>> LISTENERS = new HashMap<>();

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void handle(SpellHitBlockEvent event) {
        var spellId = event.getSpellId();
        if (!LISTENERS.containsKey(spellId)) {
            return;
        }

        var listeners = LISTENERS.getOrDefault(spellId, new ArrayList<>());
        listeners.forEach(listener -> listener.onHitBlock(event.getLevel(), event));
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void handle(SpellHitEntityEvent event) {
        var spellId = event.getSpellId();
        if (!LISTENERS.containsKey(spellId)) {
            return;
        }

        var listeners = LISTENERS.getOrDefault(spellId, new ArrayList<>());
        listeners.forEach(listener -> listener.onHitEntity(event.getLevel(), event));
    }

    public SpellHitListener(String spellId) {
        var listeners = LISTENERS.getOrDefault(spellId, new ArrayList<>());
        listeners.add(this);
        LISTENERS.put(spellId, listeners);
    }

    public SpellHitListener(List<String> spellIds) {
        spellIds.forEach(spellId -> {
            var listeners = LISTENERS.getOrDefault(spellId, new ArrayList<>());
            listeners.add(this);
            LISTENERS.put(spellId, listeners);
        });
    }

    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
    }

    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
    }
}
