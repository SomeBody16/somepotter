package network.something.somemagic.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import network.something.somemagic.SomeMagic;
import network.something.somemagic.item.ItemWand;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SomeMagic.MOD_ID);

    public static final RegistryObject<ItemWand> WAND = ITEMS.register("wand", ItemWand::new);
}
