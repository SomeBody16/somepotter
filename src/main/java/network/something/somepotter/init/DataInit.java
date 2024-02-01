package network.something.somepotter.init;

import network.something.somepotter.floo.network.FlooNetworkData;
import network.something.somepotter.floo.network.FlooSortData;
import network.something.somepotter.mechanics.spell_point.SpellPointData;
import network.something.somepotter.spells.spell.protego_maxima.claim.Claim;
import network.something.somepotter.spells.spell.protego_maxima.claim.ClaimFriends;

public class DataInit {

    public static void init() {
        Claim.Data.init();
        ClaimFriends.Data.init();
        SpellPointData.init();
        FlooNetworkData.init();
        FlooSortData.init();
    }

}
