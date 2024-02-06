package network.something.somepotter.spells.spell.protego_maxima.claim;

import ca.lukegrahamlandry.lib.data.impl.GlobalDataWrapper;
import network.something.somepotter.SomePotter;

import java.util.*;

public class ClaimFriends {


    public static boolean has(UUID owner, UUID friend) {
        return Data.get(owner).contains(friend);
    }

    public static List<UUID> list(UUID owner) {
        return Data.get(owner);
    }

    public static void add(UUID owner, UUID friend) {
        Data.get(owner).add(friend);
        Data.setDirty();
    }

    public static void remove(UUID owner, UUID friend) {
        Data.get(owner).remove(friend);
        Data.get(friend).remove(owner);
        Data.setDirty();
    }


    public static class Data {
        public Map<UUID, List<UUID>> friends = new HashMap<>();

        public Data() {
        }

        private static GlobalDataWrapper<Data> DATA;

        public static void init() {
            DATA = new GlobalDataWrapper<>(ClaimFriends.Data.class)
                    .dir(SomePotter.MOD_ID)
                    .named("protego_maxima.friends")
                    .saved();
        }

        public static List<UUID> get(UUID player) {
            DATA.get().friends.putIfAbsent(player, new ArrayList<>());
            return DATA.get().friends.get(player);
        }

        public static void setDirty() {
            DATA.setDirty();
        }
    }

}
