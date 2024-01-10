package network.something.somepotter.wand;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.init.KeyInit;
import network.something.somepotter.util.WandUtil;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class GestureListener {

    public static KeyMapping SPELL_DRAW_KEY, SPELL_CAST_KEY;
    public static final GestureHandler gestureHandler = new GestureHandler();

    public static void registerKeys() {
        SPELL_DRAW_KEY = KeyInit.registerKey("spell_draw",
                "key.categories." + SomePotter.MOD_ID, InputConstants.KEY_GRAVE);
        SPELL_CAST_KEY = KeyInit.registerKey("spell_cast",
                "key.categories." + SomePotter.MOD_ID, InputConstants.KEY_G);
    }

    protected static boolean wasSpellDrawDown = false;
    protected static boolean wasSpellCastDown = false;
    protected static boolean wasMouseDown = false;
    protected static boolean isMouseDown = false;

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        var player = Minecraft.getInstance().player;
        if (player == null) return;

        var mouseHandler = Minecraft.getInstance().mouseHandler;
        var x = (float) mouseHandler.xpos();
        var y = (float) mouseHandler.ypos();

        var isPlayerHoldingWand = WandUtil.isPlayerHoldingWand(player);

        if (isPlayerHoldingWand && SPELL_DRAW_KEY.isDown()) {
            if (!wasSpellDrawDown) {
                gestureHandler.initRecording();
                gestureHandler.startRecording();
                wasSpellDrawDown = true;
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
            wasSpellDrawDown = false;
        }

        if (SPELL_CAST_KEY.isDown() && !wasSpellCastDown) {
            new SpellCastPacket().sendToServer();
            wasSpellCastDown = true;
        }
        if (!SPELL_CAST_KEY.isDown() && wasSpellCastDown) {
            wasSpellCastDown = false;
        }
    }

    @SubscribeEvent
    public static void onMouseInput(InputEvent.RawMouseEvent event) {
        if (event.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT
                && event.getAction() == GLFW.GLFW_RELEASE) {
            isMouseDown = false;
        }
        if (gestureHandler.isRecording()
                && event.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT
                && event.getAction() == GLFW.GLFW_PRESS) {
            event.setCanceled(true);
            isMouseDown = true;
        }
    }

}
