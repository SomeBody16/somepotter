package network.something.somepotter.mechanics.gesture;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.init.KeyInit;
import network.something.somepotter.mechanics.spell_queue.SpellCastPacket;
import network.something.somepotter.mechanics.spell_queue.SpellQueueManager;
import network.something.somepotter.util.KeyWrapper;
import network.something.somepotter.util.WandUtil;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class GestureListener {

    public static KeyWrapper SPELL_DRAW_KEY, SPELL_CAST_KEY, SPELL_CLEAR_KEY;
    public static final GestureHandler gestureHandler = new GestureHandler();

    public static void registerKeys() {
        spellDrawKey();
        spellCastKey();
        spellClearKey();
    }

    protected static void spellDrawKey() {
        SPELL_DRAW_KEY = new KeyWrapper(SomePotter.MOD_ID, "spell_draw", KeyInit.CATEGORY, InputConstants.KEY_GRAVE);
        SPELL_DRAW_KEY.onPress(player -> {
            if (!canDraw(player)) return;
            var mouseHandler = Minecraft.getInstance().mouseHandler;
            mouseHandler.releaseMouse();
            gestureHandler.initRecording();
            gestureHandler.startRecording();
        });
        SPELL_DRAW_KEY.onRelease(player -> {
            var mouseHandler = Minecraft.getInstance().mouseHandler;
            mouseHandler.grabMouse();
            gestureHandler.stopRecordingAndRecognize();
            GestureHud.shuffleColors();
        });
        SPELL_DRAW_KEY.onHeldTick(player -> {
            var mouseHandler = Minecraft.getInstance().mouseHandler;
            if (isMouseDown) {
                var x = (float) mouseHandler.xpos();
                var y = (float) mouseHandler.ypos();
                gestureHandler.recordPoint(x, y);
            }
        });
    }

    protected static void spellCastKey() {
        SPELL_CAST_KEY = new KeyWrapper(SomePotter.MOD_ID, "spell_cast", KeyInit.CATEGORY, InputConstants.KEY_G);
        SPELL_CAST_KEY.onPress(player -> {
            var spellId = SpellQueueManager.shift().getId();
            new SpellCastPacket(spellId).sendToServer();
        });
    }

    protected static void spellClearKey() {
        SPELL_CLEAR_KEY = new KeyWrapper(SomePotter.MOD_ID, "spell_clear", KeyInit.CATEGORY);
        SPELL_CLEAR_KEY.onPress(player -> {
            SpellQueueManager.clear();
        });
    }

    protected static boolean canDraw(Player player) {
        return WandUtil.isPlayerHoldingWand(player);
    }


    protected static boolean isMouseDown = false;

    public static void onMouseLeftPressed() {
        if (gestureHandler.isRecording()) {
            gestureHandler.nextStroke();
        }
    }

    @SubscribeEvent
    public static void onMouseInput(InputEvent.RawMouseEvent event) {
        if (event.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            if (event.getAction() == GLFW.GLFW_PRESS) {
                if (!isMouseDown) {
                    onMouseLeftPressed();
                }
                isMouseDown = true;
            }
            if (event.getAction() == GLFW.GLFW_RELEASE) {
                isMouseDown = false;
            }
        }

        if (gestureHandler.isRecording()
                && event.getAction() == GLFW.GLFW_PRESS) {
            event.setCanceled(true);
            isMouseDown = true;
        }
    }

}
