package network.something.somepotter.mechanics.spell_learn;

import ca.lukegrahamlandry.lib.network.ClientSideHandler;
import ca.lukegrahamlandry.lib.network.ServerSideHandler;
import net.minecraft.server.level.ServerPlayer;
import network.something.somepotter.init.SpellInit;

import java.util.HashMap;
import java.util.Map;

public class SpellLearnClient {

    // spellId -> learned
    protected static Map<String, Boolean> clientCache = new HashMap<>();

    public static void requestFromServer() {
        new SpellLearnCachePacket().sendToServer();
    }

    public static void updateCache(Map<String, Boolean> learned) {
        clientCache = learned;
    }

    public static boolean isLearned(String spellId) {
        return clientCache.getOrDefault(spellId, false);
    }

    public static void learn(String spellId) {
        new SpellLearnPacket(spellId).sendToServer();
    }
}

class SpellLearnPacket implements ServerSideHandler {

    protected String spellId;

    public SpellLearnPacket(String spellId) {
        this.spellId = spellId;
    }

    @Override
    public void handle(ServerPlayer serverPlayer) {
        SpellLearnManager.learn(serverPlayer, spellId);
    }
}


class SpellLearnCachePacket implements ServerSideHandler, ClientSideHandler {

    protected Map<String, Boolean> learned = new HashMap<>();

    // ClientSideHandler
    @Override
    public void handle() {
        SpellLearnClient.updateCache(learned);
    }

    // ServerSideHandler
    @Override
    public void handle(ServerPlayer serverPlayer) {
        for (var spell : SpellInit.all()) {
            learned.put(spell.getId(), SpellLearnManager.isLearned(serverPlayer, spell));
        }
        sendToClient(serverPlayer);
    }
}
