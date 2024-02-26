package network.something.somepotter.mechanics.gesture;

import com.github.cluelab.dollar.Point;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import network.something.somepotter.SomePotter;
import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.spells.spell_type.charm.CharmType;
import network.something.somepotter.spells.spell_type.conjuration.ConjurationType;
import network.something.somepotter.spells.spell_type.counter_spell.CounterSpellType;
import network.something.somepotter.spells.spell_type.curse.CurseType;
import network.something.somepotter.spells.spell_type.hex.HexType;
import network.something.somepotter.spells.spell_type.jinx.JinxType;
import network.something.somepotter.spells.spell_type.transfiguration.TransfigurationType;
import network.something.somepotter.util.ScreenUtil;
import network.something.somepotter.util.ScreenVFXHelper;
import network.something.somepotter.util.WandUtil;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GestureHud {

    public static final List<Color> LINE_COLORS = Arrays.asList(
            SpellTypeInit.get(CharmType.ID).getColor(),
            SpellTypeInit.get(ConjurationType.ID).getColor(),
            SpellTypeInit.get(TransfigurationType.ID).getColor(),
            SpellTypeInit.get(JinxType.ID).getColor(),
            SpellTypeInit.get(HexType.ID).getColor(),
            SpellTypeInit.get(CurseType.ID).getColor(),
            SpellTypeInit.get(CounterSpellType.ID).getColor()
    );

    static {
        shuffleColors();
    }

    public static void shuffleColors() {
        Collections.shuffle(LINE_COLORS);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        OverlayRegistry.registerOverlayTop("Spell Gesture HUD", GestureHud::render);
    }

    protected static Font font;

    public static void render(ForgeIngameGui gui, PoseStack stack,
                              float partialTicks, int screenWidth, int screenHeight) {
        var player = Minecraft.getInstance().player;
        if (player == null || !WandUtil.isPlayerHoldingWand(player)) return;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        font = Minecraft.getInstance().font;

        var recordedPoints = GestureListener.gestureHandler.getRecordedPoints();
        var points = getTransformedPoints(recordedPoints, screenWidth / 2, screenHeight / 2);

        if (GestureListener.SPELL_DRAW_KEY.isDown() && points.size() <= 1) {
            drawCastText(stack, screenWidth, screenHeight);
        }

        if (points.size() > 1) {
            drawPoints(stack, points);
        }
    }

    protected static void drawPoints(PoseStack stack, List<Point> points) {
        if (points.size() < 2) return; // No points or only one point to draw

        var point1 = points.get(0);
        int currentStrokeId = point1.StrokeID;
        var color = getLineColor(currentStrokeId);

        for (int i = 1; i < points.size(); i++) {
            var point2 = points.get(i);

            // Check if the next point is part of the same stroke
            if (point2.StrokeID == currentStrokeId) {
                var x1 = (int) point1.X;
                var y1 = (int) point1.Y;
                var x2 = (int) point2.X;
                var y2 = (int) point2.Y;

                ScreenUtil.drawLine(stack, x1, y1, x2, y2, color.getRGB()); // Draw line with stroke-specific color
                for (var point : ScreenUtil.getLinePoints(x1, y1, x2, y2)) {
                    ScreenVFXHelper.point(stack,
                            new ResourceLocation(SomePotter.MOD_ID, "textures/vfx/infernal.png"),
                            true, point.x - 2, point.y - 2, color);
                }
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
        return Stream.of(points)
                .map(point -> new Point(point.X / 3, point.Y / 3, point.StrokeID))
                .toList();
    }

    protected static Color getLineColor(int strokeId) {
        return LINE_COLORS.get(strokeId % LINE_COLORS.size());
    }
}
