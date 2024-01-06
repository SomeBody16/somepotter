package network.something.somepotter.wand;

import ca.lukegrahamlandry.lib.keybind.KeybindWrapper;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class WandListener {

    public static KeybindWrapper SPELL_CAST_KEY;

    public static void registerKeys() {
        SPELL_CAST_KEY = KeybindWrapper.of("spell_cast", SomePotter.MOD_ID, InputConstants.KEY_GRAVE)
                .onHeldTick(WandListener::onSpellCastHeld)
                .onPress(WandListener::onSpellCastPress)
                .onRelease(WandListener::onSpellCastRelease);
    }

    public static void onSpellCastPress(Player player) {
        var mainHand = player.getMainHandItem();
        var offHand = player.getOffhandItem();
        var playerHoldingWand = isItemWand(mainHand) || isItemWand(offHand);

        var mc = Minecraft.getInstance();
        var thereIsNoScreenOpen = mc.screen == null;

        if (playerHoldingWand && thereIsNoScreenOpen) {
            mc.setScreen(new GestureScreen());
//            mc.mouseHandler.releaseMouse();

//            GestureHandler.INSTANCE.startRecording();
        }
    }

    protected static boolean wasMouseDown = false;

    public static void onSpellCastHeld(Player player) {
        var mc = Minecraft.getInstance();
        var xPos = (float) mc.mouseHandler.xpos();
        var yPos = (float) mc.mouseHandler.ypos();

        var isMouseDown = mc.mouseHandler.isRightPressed();

        if (isMouseDown && !wasMouseDown) {
            SomePotter.LOGGER.info("New stroke");
//            GestureHandler.INSTANCE.nextStroke();
        }
        if (isMouseDown) {
            wasMouseDown = true;
//            GestureHandler.INSTANCE.recordPoint(xPos, yPos);
        }
        if (!isMouseDown && wasMouseDown) {
            wasMouseDown = false;
        }
    }

    public static void onSpellCastRelease(Player player) {
//        var mc = Minecraft.getInstance();
//        mc.mouseHandler.grabMouse();
//        mc.setScreen(null);

//        GestureHandler.INSTANCE.stopRecordingAndRecognize();
    }

    protected static boolean isItemWand(ItemStack item) {
        return true;
    }

}
