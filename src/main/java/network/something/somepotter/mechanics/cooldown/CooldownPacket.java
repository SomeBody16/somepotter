package network.something.somepotter.mechanics.cooldown;

import ca.lukegrahamlandry.lib.network.ClientSideHandler;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;

public class CooldownPacket implements ClientSideHandler {

    protected Map<String, CooldownClient.Cooldown> cache = new HashMap<>();

    @Override
    public void handle() {
        CooldownClient.updateCache(cache);
    }

    @Override
    public void sendToClient(ServerPlayer serverPlayer) {
        var cooldowns = CooldownManager.getCooldowns(serverPlayer.getUUID());

        for (var entry : cooldowns.entrySet()) {
            var spellId = entry.getKey();
            var progress = entry.getValue().ticksRemaining().get() / (float) entry.getValue().ticks();
            var cooldown = new CooldownClient.Cooldown(spellId, progress);
            cache.put(spellId, cooldown);
        }

        ClientSideHandler.super.sendToClient(serverPlayer);
    }
}
