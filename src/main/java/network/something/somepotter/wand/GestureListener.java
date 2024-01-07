package network.something.somepotter.wand;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.init.KeyInit;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class GestureListener {

    public static KeyMapping SPELL_CAST_KEY;
    public static final GestureHandler gestureHandler = new GestureHandler();

    public static void registerKeys() {
        SPELL_CAST_KEY = KeyInit.registerKey("spell_cast",
                "key.categories." + SomePotter.MOD_ID, InputConstants.KEY_GRAVE);
    }

    protected static boolean wasSpellCastDown = false;
    protected static boolean wasMouseDown = false;

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        var player = Minecraft.getInstance().player;
        if (player == null) return;

        var mouseHandler = Minecraft.getInstance().mouseHandler;
        var x = (float) mouseHandler.xpos();
        var y = (float) mouseHandler.ypos();

        var isPlayerHoldingWand = WandListener.isPlayerHoldingWand(player);
        var isMouseDown = mouseHandler.isLeftPressed();

        if (isPlayerHoldingWand && SPELL_CAST_KEY.isDown()) {
            if (!wasSpellCastDown) {
                gestureHandler.initRecording();
                gestureHandler.startRecording();
                wasSpellCastDown = true;
            }

            if (isMouseDown) {
                gestureHandler.recordPoint(x, y);
                wasMouseDown = true;
            }
            if (!isMouseDown && wasMouseDown) {
                gestureHandler.nextStroke();
                wasMouseDown = false;
            }
        } else {
            gestureHandler.stopRecordingAndRecognize();
            wasSpellCastDown = false;
        }
    }

}
