package network.something.somepotter;

import ca.lukegrahamlandry.lib.config.ConfigWrapper;
import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import network.something.somepotter.init.ItemInit;
import network.something.somepotter.init.SoundInit;
import org.slf4j.Logger;

import java.util.function.Supplier;

@Mod(SomePotter.MOD_ID)
public class SomePotter {
    public static final String MOD_ID = "somepotter";

    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Supplier<SomePotterConfig> CONFIG = ConfigWrapper.synced(SomePotterConfig.class);

    public SomePotter() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemInit.register(bus);
        SoundInit.register(bus);
    }
}
