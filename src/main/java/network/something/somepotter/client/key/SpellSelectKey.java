package network.something.somepotter.client.key;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.client.KeyInit;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpellSelectKey {

    public static final KeyMapping KEY = KeyInit.key("spell_select", KeyInit.Category.SPELL, InputConstants.KEY_B);

    @SubscribeEvent
    public static void tick(TickEvent.ClientTickEvent event) {
        var player = Minecraft.getInstance().player;
        if (event.phase == TickEvent.Phase.END
                && KEY.consumeClick()
                && player != null
        ) {

        }
    }
}
