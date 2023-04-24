package network.something.somepotter.client;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.spell.spells.SpellKeyInit;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class KeyInit {
    public static final class Category {
        public static final String SPELL = "key.category.somepotter.spell";
    }

    public static void register() {
        SpellKeyInit.register();
    }

    public static KeyMapping key(String name, String category, int keyCode) {
        return new KeyMapping("key." + SomePotter.MOD_ID + "." + name, keyCode, category);
    }

}
