package network.something.somepotter.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import network.something.somepotter.SomePotter;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemInit {

    protected static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SomePotter.MOD_ID);

    public static final RegistryObject<Item> WAND = ITEMS.register(
            "wand",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS))
    );

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }


}
