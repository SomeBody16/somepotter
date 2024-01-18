package network.something.somepotter.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import network.something.somepotter.SomePotter;
import network.something.somepotter.floo.minecraft.FlooPowderItem;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemInit {

    protected static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SomePotter.MOD_ID);

    public static final ItemInit.CreativeTab CREATIVE_TAB = new ItemInit.CreativeTab();

    public static final RegistryObject<Item> WAND = ITEMS.register(
            "wand",
            () -> new Item(new Item.Properties().tab(CREATIVE_TAB))
    );

    public static final RegistryObject<Item> FLOO_POWDER = ITEMS.register(FlooPowderItem.ID, FlooPowderItem::new);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

    public static class CreativeTab extends CreativeModeTab {
        public CreativeTab() {
            super(SomePotter.MOD_ID);
        }

        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(WAND.get());
        }
    }
}
