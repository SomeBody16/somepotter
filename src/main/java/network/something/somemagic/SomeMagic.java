package network.something.somemagic;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import network.something.somemagic.init.EntityInit;
import network.something.somemagic.init.ItemInit;
import org.slf4j.Logger;
 
@Mod(SomeMagic.MOD_ID)
public class SomeMagic {
    public static final String MOD_ID = "somemagic";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SomeMagic() {
        LOGGER.info("Starting SomeMagic...");

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemInit.ITEMS.register(modEventBus);
        EntityInit.ENTITIES.register(modEventBus);
    }
}
