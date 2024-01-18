package network.something.somepotter.integration;

import network.something.somepotter.integration.patchouli.PatchouliIntegration;
import network.something.somepotter.integration.pehkui.PehkuiIntegration;
import network.something.somepotter.integration.the_vault.TheVaultIntegration;

import java.util.ArrayList;
import java.util.List;

public class Integrations {
    protected static List<Integration> INTEGRATIONS = new ArrayList<>();

    public static PatchouliIntegration PATCHOULI = register(new PatchouliIntegration());
    public static TheVaultIntegration THE_VAULT = register(new TheVaultIntegration());
    public static PehkuiIntegration PEHKUI = register(new PehkuiIntegration());

    public static void init() {
        INTEGRATIONS.forEach(Integration::init);
    }

    protected static <T extends Integration> T register(T integration) {
        INTEGRATIONS.add(integration);
        return integration;
    }

}
