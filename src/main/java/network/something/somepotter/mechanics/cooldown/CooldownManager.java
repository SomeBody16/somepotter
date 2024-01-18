package network.something.somepotter.mechanics.cooldown;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.event.SpellCastEvent;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CooldownManager {

    public record Cooldown(UUID uuid, String spellId, int ticks, AtomicInteger ticksRemaining) {
    }

    // player uuid -> spell id -> Cooldown
    protected static final Map<UUID, Map<String, Cooldown>> COOLDOWN = new HashMap<>();
    protected static final List<ServerPlayer> TO_NOTIFY = new ArrayList<>();

    public static void set(UUID uuid, String spellId, int ticks) {
        var cooldown = new Cooldown(uuid, spellId, ticks, new AtomicInteger(ticks));
        COOLDOWN.computeIfAbsent(uuid, (key) -> new HashMap<>())
                .put(spellId, cooldown);
    }

    public static int getRemainingTicks(UUID uuid, String spellId) {
        if (!COOLDOWN.containsKey(uuid)) return 0;
        if (!COOLDOWN.get(uuid).containsKey(spellId)) return 0;
        return COOLDOWN.get(uuid).get(spellId).ticksRemaining.get();
    }

    public static Map<String, Cooldown> getCooldowns(UUID uuid) {
        return COOLDOWN.getOrDefault(uuid, new HashMap<>());
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        COOLDOWN.forEach((uuid, spellIdToCooldown) -> {
            spellIdToCooldown.forEach((spellId, cooldown) -> {
                if (cooldown.ticksRemaining.get() > 0) {
                    cooldown.ticksRemaining.decrementAndGet();
                    spellIdToCooldown.put(spellId, cooldown);
                }
            });
            spellIdToCooldown.values().removeIf(cooldown -> cooldown.ticksRemaining.get() <= 0);
        });
        COOLDOWN.values().removeIf(Map::isEmpty);

        // Notify players
        TO_NOTIFY.removeIf(player -> {
            if (!COOLDOWN.containsKey(player.getUUID())) {
                new CooldownPacket().sendToClient(player);
                return true;
            }
            return false;
        });
        TO_NOTIFY.forEach(player -> new CooldownPacket().sendToClient(player));
    }

    @SubscribeEvent
    public static void onCast(SpellCastEvent.Post<?> event) {
        if (event.caster instanceof ServerPlayer player) {
            TO_NOTIFY.add(player);
        }
    }
}
