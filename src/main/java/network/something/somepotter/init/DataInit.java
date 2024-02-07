package network.something.somepotter.init;

import network.something.somepotter.floo.network.FlooNetworkData;
import network.something.somepotter.floo.network.FlooSortData;
import network.something.somepotter.mechanics.spell_point.SpellPointData;
import network.something.somepotter.spells.spell.protego_maxima.claim.ClaimManager;

public class DataInit {

    public static void init() {
        ClaimManager.init();
        SpellPointData.init();
        FlooNetworkData.init();
        FlooSortData.init();
    }

}
