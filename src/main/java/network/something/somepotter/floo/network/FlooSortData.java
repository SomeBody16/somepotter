package network.something.somepotter.floo.network;

import ca.lukegrahamlandry.lib.data.DataWrapper;
import ca.lukegrahamlandry.lib.data.impl.PlayerDataWrapper;
import net.minecraft.server.level.ServerPlayer;
import network.something.somepotter.SomePotter;

import java.util.ArrayList;
import java.util.List;

public class FlooSortData {

    protected static PlayerDataWrapper<FlooSortData> DATA;

    public static void init() {
        DATA = DataWrapper.player(FlooSortData.class)
                .dir(SomePotter.MOD_ID)
                .named("floo_network.sort")
                .saved();
    }

    public static List<String> get(ServerPlayer player) {
        return DATA.get(player).sortedNames;
    }

    public static void setDirty(ServerPlayer player) {
        DATA.setDirty(player);
    }

    public List<String> sortedNames = new ArrayList<>();
}
