package network.something.somepotter.gesture.hud;

import com.github.cluelab.dollar.Point;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import network.something.somepotter.SomePotter;
import network.something.somepotter.util.ScreenUtil;
import network.something.somepotter.wand.WandGestureListener;

import java.util.List;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GestureHud {

    public static final List<Integer> LINE_COLORS = ImmutableList.of(
            0xFFFFD700, // Gold
            0xFF99ff00, // Lime
            0xFFff00ff, // Magenta
            0xFF00ffff, // Cyan
            0xFFff0000  // Red
    );

    protected static int LINE_COLOR_INDEX = 0;

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        OverlayRegistry.registerOverlayTop("Spell Gesture HUD", GestureHud::render);
    }

    protected static Font font;

    public static void render(ForgeIngameGui gui, PoseStack stack,
                              float partialTicks, int screenWidth, int screenHeight) {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        font = Minecraft.getInstance().font;

        var recordedPoints = WandGestureListener.gestureHandler.getRecordedPoints();
        var points = getTransformedPoints(recordedPoints, screenWidth / 2, screenHeight / 2);

        if (WandGestureListener.SPELL_DRAW_KEY.isDown() && points.size() <= 1) {
            drawCastText(stack, screenWidth, screenHeight);
        }

        if (points.size() > 1) {
            drawPoints(stack, points);
            drawMousePoint(stack, recordedPoints, screenWidth / 2, screenHeight / 2);
        }
    }

    protected static void drawMousePoint(PoseStack stack, Point[] points, int centerX, int centerY) {
        var mouseHandler = Minecraft.getInstance().mouseHandler;
        var pointCenter = getCenter(points);
        var x = centerX + (int) (mouseHandler.xpos() - pointCenter.X);
        var y = centerY + (int) (mouseHandler.ypos() - pointCenter.Y);
        GuiComponent.fill(stack, x - 1, y - 1, x + 1, y + 1, 0xFFFFFFFF);
    }

    protected static void drawPoints(PoseStack stack, List<Point> points) {
        if (points.size() < 2) return; // No points or only one point to draw

        var point1 = points.get(0);
        int currentStrokeId = point1.StrokeID;
        int color = getLineColor(currentStrokeId);

        for (int i = 1; i < points.size(); i++) {
            var point2 = points.get(i);

            // Check if the next point is part of the same stroke
            if (point2.StrokeID == currentStrokeId) {
                var x1 = (int) point1.X;
                var y1 = (int) point1.Y;
                var x2 = (int) point2.X;
                var y2 = (int) point2.Y;

                ScreenUtil.drawLine(stack, x1, y1, x2, y2, color); // Draw line with stroke-specific color
            } else {
                // Update currentStrokeId and color for the new stroke
                currentStrokeId = point2.StrokeID;
                color = getLineColor(currentStrokeId);
            }

            point1 = point2; // Update point1 for the next iteration
        }
    }

    protected static void drawCastText(PoseStack stack, int screenWidth, int screenHeight) {
        GuiComponent.drawCenteredString(stack, font, "Cast a Spell",
                screenWidth / 2, screenHeight / 6 - 50, 0xFFFFFFFF);
    }

    protected static List<Point> getTransformedPoints(Point[] points, int centerX, int centerY) {
        try {
            var pointsCenter = getCenter(points);

            return Stream.of(points)
                    .map(point -> {
                        var newX = centerX + (point.X - pointsCenter.X);
                        var newY = centerY + (point.Y - pointsCenter.Y);
                        return new Point(newX, newY, point.StrokeID);
                    })
                    .toList();
        } catch (ArithmeticException e) {
            return List.of(points);
        }
    }

    protected static Point getCenter(Point[] points) {
        // Calculate the average coordinates of all points
        long totalX = 0, totalY = 0, count = 0;
        for (Point point : points) {
            if (point.StrokeID == 0) {
                totalX += (long) point.X;
                totalY += (long) point.Y;
                count++;
            }
        }

        var avgX = totalX / count;
        var avgY = totalY / count;
        return new Point(avgX, avgY, 0);
    }

    protected static int getLineColor(int strokeId) {
        return LINE_COLORS.get(strokeId % LINE_COLORS.size());
    }
}
