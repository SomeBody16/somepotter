package network.something.somepotter.init;

import network.something.somepotter.floo.network.FlooNetworkConfig;
import network.something.somepotter.integration.the_vault.TheVaultConfig;
import network.something.somepotter.mechanics.cooldown.CooldownConfig;
import network.something.somepotter.spells.spell.accio.AccioSpell;
import network.something.somepotter.spells.spell.basic_cast.BasicCastSpell;
import network.something.somepotter.spells.spell.protego_maxima.ProtegoMaximaSpell;

public class ConfigInit {

    public static void init() {
        TheVaultConfig.get();
        AccioSpell.CONFIG.get();
        BasicCastSpell.CONFIG.get();
        CooldownConfig.get();
        ProtegoMaximaSpell.CONFIG.get();
        FlooNetworkConfig.get();
    }

}
