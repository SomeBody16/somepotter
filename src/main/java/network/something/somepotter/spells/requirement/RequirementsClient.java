package network.something.somepotter.spells.requirement;

import ca.lukegrahamlandry.lib.network.ClientSideHandler;
import ca.lukegrahamlandry.lib.network.ServerSideHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import network.something.somepotter.init.SpellInit;

import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class RequirementsClient {

    // spellId -> requirementId -> isMet
    protected static Map<String, Map<String, Boolean>> clientCache = new HashMap<>();

    public static void requestFromServer(String spellId) {
        new RequirementsPacket(spellId).sendToServer();
    }

    public static void updateCache(String spellId, Map<String, Boolean> requirements) {
        clientCache.put(spellId, requirements);
    }

    public static boolean isMet(String spellId, String requirementId) {
        return clientCache
                .getOrDefault(spellId, new HashMap<>())
                .getOrDefault(requirementId, false);
    }

    public static boolean isMet(String spellId, Requirement requirement) {
        return isMet(spellId, requirement.getId());
    }

    public static boolean isMet(String spellId) {
        if (!clientCache.containsKey(spellId)) return false;
        if (clientCache.get(spellId).isEmpty()) return false;

        for (var requirement : clientCache.getOrDefault(spellId, new HashMap<>()).values()) {
            if (!requirement) return false;
        }
        return true;
    }

}

class RequirementsPacket implements ServerSideHandler, ClientSideHandler {

    protected String spellId;
    protected Map<String, Boolean> requirements = new HashMap<>();

    public RequirementsPacket(String spellId) {
        this.spellId = spellId;
    }


    // ClientSideHandler
    @Override
    public void handle() {
        RequirementsClient.updateCache(spellId, requirements);
    }

    // ServerSideHandler
    @Override
    public void handle(ServerPlayer serverPlayer) {
        var spell = SpellInit.get(spellId);
        for (var requirement : spell.getRequirements()) {
            requirements.put(requirement.getId(), requirement.isMet(serverPlayer));
        }
        sendToClient(serverPlayer);
    }
}
