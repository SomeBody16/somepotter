package network.something.somepotter.spells.spell.protego_maxima.claim;

import ca.lukegrahamlandry.lib.data.impl.GlobalDataWrapper;
import net.minecraft.server.level.ServerPlayer;
import network.something.somepotter.SomePotter;

import java.util.*;

public class ClaimFriends {


    public static boolean has(ServerPlayer player, ServerPlayer friend) {
        return Data.get(player).contains(friend.getUUID());
    }

    public static void add(ServerPlayer player, ServerPlayer friend) {
        Data.get(player).add(friend.getUUID());
        Data.setDirty();
    }

    public static void remove(ServerPlayer player, ServerPlayer friend) {
        Data.get(player).remove(friend.getUUID());
        Data.get(friend).remove(player.getUUID());
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

        public static List<UUID> get(ServerPlayer player) {
            DATA.get().friends.putIfAbsent(player.getUUID(), new ArrayList<>());
            return DATA.get().friends.get(player.getUUID());
        }

        public static void setDirty() {
            DATA.setDirty();
        }
    }

}
