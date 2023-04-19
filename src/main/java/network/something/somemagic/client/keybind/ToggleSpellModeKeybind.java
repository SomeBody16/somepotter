package network.something.somemagic.client.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somemagic.SomeMagic;
import network.something.somemagic.client.KeyInit;

@Mod.EventBusSubscriber(modid = SomeMagic.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ToggleSpellModeKeybind {
    public static KeyMapping KEY = KeyInit.key("toggle_spell_mode", KeyInit.Category.MAIN, InputConstants.KEY_B);

    public static void handle(TickEvent.ClientTickEvent event) {
        SomeMagic.LOGGER.info("TOGGLE SPELL MODE");
        Player player = Minecraft.getInstance().player;
        
    }

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            while (KEY.consumeClick()) {
                handle(event);
            }
        }
    }

}
