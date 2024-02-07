package network.something.somepotter.spells.spell.protego_maxima.claim;

import ca.lukegrahamlandry.lib.data.DataWrapper;
import ca.lukegrahamlandry.lib.data.impl.LevelDataWrapper;
import ca.lukegrahamlandry.lib.network.ClientSideHandler;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import network.something.somepotter.SomePotter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ClaimData {

    protected static LevelDataWrapper<ClaimData> DATA;
    static ClaimData cachedData = new ClaimData();

    protected static ClaimData getData(Level level) {
        ClaimData result;
        try {
            result = DATA.get(level);
        } catch (Exception e) {
            result = cachedData;
        }

        return result == null ? new ClaimData() : result;
    }

    public static void init() {
        DATA = DataWrapper.level(ClaimData.class)
                .dir(SomePotter.MOD_ID)
                .named("protego_maxima.claim_data")
                .synced()
                .saved();
    }

    public static void sync(Level level) {
//        new ClaimDataPacket(getData(level)).sendToAllClients();
    }

    public static Map<ChunkPos, Claim> get(Level level) {
        var result = new HashMap<ChunkPos, Claim>();

        for (var x : getData(level).claims.keySet()) {
            for (var z : getData(level).claims.get(x).keySet()) {
                result.put(new ChunkPos(x, z), getData(level).claims.get(x).get(z));
            }
        }

        return result;
    }

    public static void set(Level level, ChunkPos pos, Claim claim) {
        DATA.get(level).claims.computeIfAbsent(pos.x, k -> new HashMap<>());
        DATA.get(level).claims.get(pos.x).put(pos.z, claim);
        setDirty(level);
    }

    public static void update(Level level, Consumer<Claim> update) {
        get(level).forEach((pos, claim) -> update.accept(claim));
        setDirty(level);
    }

    public static void remove(Level level, ChunkPos pos) {
        DATA.get(level).claims.computeIfAbsent(pos.x, k -> new HashMap<>());
        DATA.get(level).claims.get(pos.x).remove(pos.z);
        DATA.get(level).claims.entrySet().removeIf(entry -> entry.getValue().isEmpty());
        setDirty(level);
    }

    public static void setDirty(Level level) {
        DATA.setDirty();
        sync(level);
    }

    public Map<Integer, Map<Integer, Claim>> claims = new HashMap<>();

}

class ClaimDataPacket implements ClientSideHandler {

    protected ClaimData data;

    public ClaimDataPacket(ClaimData data) {
        this.data = data;
    }

    @Override
    public void handle() {
        ClaimData.cachedData = data;
    }
}
