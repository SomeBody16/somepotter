package network.something.somepotter.spells.spell.protego_maxima.claim;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;

import javax.annotation.Nullable;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

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

    public static boolean hasAccess(BlockPos pos, Player player) {
        return hasAccess(new ChunkPos(pos), player);
    }

    public static boolean hasAccess(ChunkPos chunkPos, Player player) {
        return hasAccess(chunkPos.x, chunkPos.z, player);
    }

    public static boolean hasAccess(int chunkX, int chunkZ, Player player) {
        return isClaimed(chunkX, chunkZ)
                && cache.get(chunkX).get(chunkZ).owner != null
                && cache.get(chunkX).get(chunkZ).owner.equals(player.getUUID());
    }

    @Nullable
    public static Claim get(int chunkX, int chunkZ) {
        return isClaimed(chunkX, chunkZ) ? cache.get(chunkX).get(chunkZ) : null;
    }

}
