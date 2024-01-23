package network.something.somepotter;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import network.something.somepotter.init.*;
import network.something.somepotter.integration.Integrations;
import org.slf4j.Logger;

@Mod(SomePotter.MOD_ID)
public class SomePotter {
    public static final String MOD_ID = "somepotter";

    public static final Logger LOGGER = LogUtils.getLogger();

    public SomePotter() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();

        BlockInit.register(bus);
        ItemInit.register(bus);
        SoundInit.register(bus);
        EffectInit.register(bus);

        Integrations.init();
        DataInit.init();
        SpellInit.init();
    }
}
