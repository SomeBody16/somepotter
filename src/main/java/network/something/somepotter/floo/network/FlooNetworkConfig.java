package network.something.somepotter.floo.network;

import ca.lukegrahamlandry.lib.config.Comment;
import ca.lukegrahamlandry.lib.config.ConfigWrapper;
import net.minecraft.world.level.Level;
import network.something.somepotter.SomePotter;

import java.util.List;
import java.util.function.Supplier;

public class FlooNetworkConfig {

    protected static final Supplier<FlooNetworkConfig> CONFIG = ConfigWrapper.server(FlooNetworkConfig.class)
            .dir(SomePotter.MOD_ID)
            .named("floo_network");

    public static FlooNetworkConfig get() {
        return CONFIG.get();
    }

    @Comment("The dimensions that floo network fireplaces can be placed in, RegExp")
    public List<String> allowedFireplaceDimensions = List.of(
            Level.OVERWORLD.location().toString(),
            Level.NETHER.location().toString(),
            Level.END.location().toString()
    );

    @Comment("The dimensions that floo network can be used in (apparition), RegExp")
    public List<String> allowedApparitionDimensions = List.of(
            Level.OVERWORLD.location().toString(),
            Level.NETHER.location().toString(),
            Level.END.location().toString()
    );

}
