package network.something.somepotter.spells.spell.protego_maxima.claim;

import ca.lukegrahamlandry.lib.data.impl.LevelDataWrapper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import network.something.somepotter.SomePotter;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Claim {

    public ChunkPos pos;
    public UUID owner;


    public static boolean exists(ServerLevel level, ChunkPos pos) {
        return Data.get(level).containsKey(pos);
    }

    public static void add(ServerLevel level, ChunkPos pos, ServerPlayer player) {
        var claim = new Claim();
        claim.pos = pos;
        claim.owner = player.getUUID();
        Data.get(level).put(claim.pos, claim);
        Data.set(level, pos, claim);
    }

    @Nullable
    public static Claim get(ServerLevel level, ChunkPos pos) {
        return Data.get(level).getOrDefault(pos, null);
    }

    public static void remove(ServerLevel level, ChunkPos pos) {
        Data.remove(level, pos);
    }

    public static boolean hasAccess(ServerLevel level, ChunkPos chunkPos, ServerPlayer player) {
        if (player.isCreative()) return true;
        if (!exists(level, chunkPos)) return true;

        var claim = Data.get(level).get(chunkPos);
        if (claim.owner.equals(player.getUUID())) return true;

        return ClaimFriends.has(claim.owner, player.getUUID());
    }


    public static class Data {
        public Map<Integer, Map<Integer, Claim>> claims = new HashMap<>();

        public Data() {
        }

        private static LevelDataWrapper<Data> DATA;

        public static void init() {
            DATA = new LevelDataWrapper<>(Data.class)
                    .dir(SomePotter.MOD_ID)
                    .named("protego_maxima.claim")
                    .saved();
        }

        public static Map<ChunkPos, Claim> get(ServerLevel level) {
            var claims = DATA.get(level).claims;
            var result = new HashMap<ChunkPos, Claim>();

            for (var x : claims.keySet()) {
                for (var z : claims.get(x).keySet()) {
                    result.put(new ChunkPos(x, z), claims.get(x).get(z));
                }
            }

            return result;
        }

        public static void set(ServerLevel level, ChunkPos pos, Claim claim) {
            var claims = DATA.get(level).claims;
            claims.computeIfAbsent(pos.x, k -> new HashMap<>());
            claims.get(pos.x).put(pos.z, claim);
            DATA.setDirty(level);
        }

        public static void remove(ServerLevel level, ChunkPos pos) {
            DATA.get(level).claims.get(pos.x).remove(pos.z);
            if (DATA.get(level).claims.get(pos.x).isEmpty()) {
                DATA.get(level).claims.remove(pos.x);
            }
            DATA.setDirty(level);
        }
    }
}
