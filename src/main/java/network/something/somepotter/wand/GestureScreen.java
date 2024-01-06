package network.something.somepotter.wand;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import network.something.somepotter.SomePotter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class GestureScreen extends Screen {

    protected GestureHandler gestureHandler = new GestureHandler();
    protected MouseHandler mouseHandler;
    protected boolean wasMouseDown = true;

    protected GestureScreen() {
        super(new TextComponent("Gesture Screen"));
    }

    @Override
    protected void init() {
        gestureHandler.initRecording();
        mouseHandler = Minecraft.getInstance().mouseHandler;
    }

    protected void tickRecording(int mouseX, int mouseY) {
        gestureHandler.recordPoint(mouseX, mouseY);


//        var isMouseDown = mouseHandler.isLeftPressed();
//
//        if (isMouseDown && !wasMouseDown) {
//            SomePotter.LOGGER.info("New stroke");
//            gestureHandler.nextStroke();
//        }
//        if (isMouseDown) {
//            wasMouseDown = true;
//            gestureHandler.recordPoint(mouseX, mouseY);
//        }
//        if (!isMouseDown && wasMouseDown) {
//            wasMouseDown = false;
//        }
//
//        if (mouseHandler.isRightPressed()) {
//            gestureHandler.stopRecordingAndRecognize();
//            this.onClose();
//        }
    }

    @Override
    public boolean mouseClicked(double x, double y, int n) {
        SomePotter.LOGGER.info("mouseClicked: {}, {} ({})", x, y, n);
        if (n == 0) {
            gestureHandler.startRecording();
        }
        if (n == 1) {
            gestureHandler.stopRecordingAndRecognize();
            this.onClose();
        }

        return super.mouseClicked(x, y, n);
    }

    @Override
    public boolean mouseReleased(double x, double y, int n) {
        SomePotter.LOGGER.info("mouseReleased: {}, {} ({})", x, y, n);
        gestureHandler.pauseRecording();
        gestureHandler.nextStroke();

        return super.mouseReleased(x, y, n);
    }

    protected void drawRecording(PoseStack stack, int mouseX, int mouseY) {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        var points = List.of(gestureHandler.getRecordedPoints());

        if (points.isEmpty()) {
            drawCenteredString(stack, this.font, "Cast a Spell",
                    this.width / 2, this.height / 6 - 50, 16777215);
        }

        for (var point : points) {
            var x = (int) point.X;
            var y = (int) point.Y;
            fill(stack, x, y, x + 1, y + 1, 0xFFFFD700);
        }
    }

    @Override
    public void render(@NotNull PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        tickRecording(mouseX, mouseY);
        drawRecording(stack, mouseX, mouseY);
    }


}
