package network.something.somepotter.floo.network;

import ca.lukegrahamlandry.lib.data.impl.GlobalDataWrapper;
import network.something.somepotter.SomePotter;

import java.util.ArrayList;
import java.util.List;

public class FlooNetworkData {
    protected static GlobalDataWrapper<FlooNetworkData> DATA;

    public static void init() {
        DATA = new GlobalDataWrapper<>(FlooNetworkData.class)
                .dir(SomePotter.MOD_ID)
                .named("floo_network")
                .saved();
    }

    static List<FlooNode> get() {
        if (DATA.get() == null) {
            DATA.set(new FlooNetworkData());
        }
        return DATA.get().nodes;
    }

    public static void setDirty() {
        DATA.setDirty();
    }


    public List<FlooNode> nodes = new ArrayList<>();
}
