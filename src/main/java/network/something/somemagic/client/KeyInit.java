package network.something.somemagic.client;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import network.something.somemagic.SomeMagic;

@Mod.EventBusSubscriber(modid = SomeMagic.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class KeyInit {
    public static final class Category {
        public static final String MAIN = "key.category.somemagic.main";
    }

    public static void register() {
    }

    public static KeyMapping key(String name, String category, int keyCode) {
        return new KeyMapping("key." + SomeMagic.MOD_ID + "." + name, keyCode, category);
    }

}