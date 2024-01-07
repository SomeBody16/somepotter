package network.something.somepotter.wand;

import com.github.cluelab.dollar.Point;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.util.ScreenUtil;

import java.util.List;
import java.util.stream.Stream;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class GestureHud {

    public static void render(ForgeIngameGui gui, PoseStack stack,
                              float partialTicks, int screenWidth, int screenHeight) {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        var points = getTransformedPoints(screenWidth / 2, screenHeight / 2);
        if (GestureListener.SPELL_CAST_KEY.isDown() && points.isEmpty()) {
            var font = Minecraft.getInstance().font;
            GuiComponent.drawCenteredString(stack, font, "Cast a Spell",
                    screenWidth / 2, screenHeight / 6 - 50, 0xFFFFFFFF);
        }

        if (points.size() > 1) {
            for (int i = 0; i < points.size() - 1; i++) {
                var point1 = points.get(i);
                var point2 = points.get(i + 1);

                var x1 = (int) point1.X;
                var y1 = (int) point1.Y;
                var x2 = (int) point2.X;
                var y2 = (int) point2.Y;

                ScreenUtil.drawLine(stack, x1, y1, x2, y2, 0xFFFFD700); // Color in ARGB format
            }
        }
    }

    protected static List<Point> getTransformedPoints(int centerX, int centerY) {
        var points = GestureListener.gestureHandler.getRecordedPoints();
        // Calculate the average coordinates of all points
        long totalX = 0, totalY = 0;
        for (Point point : points) {
            totalX += (long) point.X;
            totalY += (long) point.Y;
        }

        try {
            var avgX = totalX / points.length;
            var avgY = totalY / points.length;

            return Stream.of(points)
                    .map(point -> {
                        var newX = centerX + (point.X - avgX);
                        var newY = centerY + (point.Y - avgY);
                        return new Point(newX, newY, point.StrokeID);
                    })
                    .toList();
        } catch (ArithmeticException e) {
            return List.of(points);
        }
    }
}
