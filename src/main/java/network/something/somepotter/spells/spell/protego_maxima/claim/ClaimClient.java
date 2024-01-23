package network.something.somepotter.spells.spell.protego_maxima.claim;

import ca.lukegrahamlandry.lib.network.ClientSideHandler;
import ca.lukegrahamlandry.lib.network.ServerSideHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import javax.annotation.Nullable;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static net.minecraft.Util.NIL_UUID;

public class ClaimClient {

    protected static LocalTime lastUpdated = LocalTime.now();


    // chunkX -> chunkZ -> Claim
    protected static Map<Integer, Map<Integer, Claim>> cache = new HashMap<>();

    protected static void sync() {
        if (lastUpdated.plusSeconds(30).isBefore(LocalTime.now())) {
            lastUpdated = LocalTime.now();
            new ClaimSyncPacket().sendToServer();
        }
    }

    public static void update(Map<Integer, Map<Integer, Claim>> cache) {
        ClaimClient.cache = cache;
    }

    public static boolean isClaimed(int chunkX, int chunkZ) {
        sync();
        return cache.containsKey(chunkX) && cache.get(chunkX).containsKey(chunkZ);
    }

    @Nullable
    public static Claim get(int chunkX, int chunkZ) {
        return isClaimed(chunkX, chunkZ) ? cache.get(chunkX).get(chunkZ) : null;
    }

}

class ClaimSyncPacket implements ServerSideHandler, ClientSideHandler {
    protected Map<Integer, Map<Integer, Claim>> claims = new HashMap<>();

    // ClientSideHandler
    @Override
    public void handle() {
        ClaimClient.update(claims);
    }

    // ServerSideHandler
    @Override
    public void handle(ServerPlayer player) {
        Claim.Data.get((ServerLevel) player.level).forEach((pos, claim) -> {
            claims.computeIfAbsent(pos.x, k -> new HashMap<>());

            var clone = new Claim();
            clone.pos = claim.pos;
            clone.owner = player.getUUID().equals(claim.owner) ? player.getUUID() : NIL_UUID;

            claims.get(pos.x).put(pos.z, clone);
        });
        sendToClient(player);
    }
}
