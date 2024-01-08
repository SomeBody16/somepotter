package network.something.somepotter.hud.gesture;

import com.github.cluelab.dollar.Point;
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
import network.something.somepotter.util.AbilityPowerUtil;
import network.something.somepotter.util.ScreenUtil;
import network.something.somepotter.wand.GestureListener;

import java.util.List;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GestureHud {

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

        var points = getTransformedPoints(screenWidth / 2, screenHeight / 2);

        if (GestureListener.SPELL_CAST_KEY.isDown() && points.size() <= 1) {
            drawCastText(stack, screenWidth, screenHeight);
            drawAbilityPower(stack, screenWidth, screenHeight);
        }

        if (points.size() > 1) {
            drawPoints(stack, points);
        }
    }

    protected static void drawPoints(PoseStack stack, List<Point> points) {
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

    protected static void drawCastText(PoseStack stack, int screenWidth, int screenHeight) {
        GuiComponent.drawCenteredString(stack, font, "Cast a Spell",
                screenWidth / 2, screenHeight / 6 - 50, 0xFFFFFFFF);
    }

    protected static void drawAbilityPower(PoseStack stack, int screenWidth, int screenHeight) {
        var abilityPower = AbilityPowerUtil.get(Minecraft.getInstance().player);
        var multiplier = (int) (AbilityPowerUtil.getMultiplier(abilityPower) * 100.0F);
        var text = String.format("Ability Power: %d (%d)", multiplier, abilityPower);
        GuiComponent.drawCenteredString(stack, font, text,
                screenWidth / 2, screenHeight / 6, 0xFFFFFFFF);
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
