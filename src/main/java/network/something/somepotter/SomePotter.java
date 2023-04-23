package network.something.somepotter;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import network.something.somepotter.init.EffectInit;
import network.something.somepotter.init.EntityInit;
import network.something.somepotter.init.ItemInit;
import network.something.somepotter.setup.ModSetup;
import org.slf4j.Logger;

@Mod(SomePotter.MOD_ID)
public class SomePotter {
    public static final String MOD_ID = "somepotter";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SomePotter() {
        LOGGER.info("Starting SomePotter...");

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(ModSetup::init);

        ItemInit.ITEMS.register(modEventBus);
        EntityInit.ENTITIES.register(modEventBus);
        EffectInit.register(modEventBus);
    }


}
