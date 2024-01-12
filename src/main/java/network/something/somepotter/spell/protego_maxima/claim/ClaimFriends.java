package network.something.somepotter.spell.protego_maxima.claim;

import ca.lukegrahamlandry.lib.data.impl.PlayerDataWrapper;
import net.minecraft.server.level.ServerPlayer;
import network.something.somepotter.SomePotter;

import java.util.*;

public class ClaimFriends {


    public static boolean has(ServerPlayer player, ServerPlayer friend) {
        return Data.get(player).contains(friend.getUUID());
    }

    public static void add(ServerPlayer player, ServerPlayer friend) {
        Data.get(player).add(friend.getUUID());
        Data.setDirty(player);
    }

    public static void remove(ServerPlayer player, ServerPlayer friend) {
        Data.get(player).remove(friend.getUUID());
        Data.get(friend).remove(player.getUUID());
        Data.setDirty(player);
    }


    public static class Data {
        public Map<UUID, List<UUID>> friends = new HashMap<>();

        public Data() {
        }

        private static PlayerDataWrapper<ClaimFriends.Data> DATA;

        public static void init() {
            new PlayerDataWrapper<>(ClaimFriends.Data.class)
                    .dir(SomePotter.MOD_ID)
                    .named("protego_maxima.friends")
                    .saved();
        }

        public static List<UUID> get(ServerPlayer player) {
            return DATA.get(player).friends.getOrDefault(player.getUUID(), new ArrayList<>());
        }

        public static void setDirty(ServerPlayer player) {
            DATA.setDirty(player);
        }
    }

}
