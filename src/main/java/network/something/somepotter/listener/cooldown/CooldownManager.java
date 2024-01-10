package network.something.somepotter.listener.cooldown;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CooldownManager {

    /**
     * Map of player uuids to a map of spell ids to cooldown ticks
     */
    protected static final Map<UUID, Map<String, Integer>> COOLDOWN = new HashMap<>();

    public static void set(UUID uuid, String spellId, int ticks) {
        COOLDOWN.computeIfAbsent(uuid, (key) -> new HashMap<>())
                .put(spellId, ticks);
    }

    public static int get(UUID uuid, String spellId) {
        return COOLDOWN
                .computeIfAbsent(uuid, (key) -> new HashMap<>())
                .getOrDefault(spellId, 0);
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        COOLDOWN.forEach((uuid, spellIdToCooldown) -> {
            spellIdToCooldown.forEach((spellId, cooldown) -> {
                if (cooldown > 0) {
                    spellIdToCooldown.put(spellId, cooldown - 1);
                }
            });
            spellIdToCooldown.values().removeIf(cooldown -> cooldown <= 0);
        });
        COOLDOWN.values().removeIf(Map::isEmpty);
    }
}
