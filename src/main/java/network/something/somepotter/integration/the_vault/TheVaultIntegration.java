package network.something.somepotter.integration.the_vault;

import net.minecraftforge.common.MinecraftForge;
import network.something.somepotter.integration.Integration;
import network.something.somepotter.integration.the_vault.listener.VaultCompletedListener;

public class TheVaultIntegration extends Integration {

    public TheVaultIntegration() {
        super("the_vault");
    }

    @Override
    protected void ifLoaded() {
        MinecraftForge.EVENT_BUS.addListener(VaultCompletedListener::onVaultLeave);
    }

}
