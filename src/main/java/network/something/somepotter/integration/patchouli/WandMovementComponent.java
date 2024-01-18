package network.something.somepotter.integration.patchouli;

import com.github.cluelab.dollar.Point;
import com.mojang.blaze3d.vertex.PoseStack;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.util.ScreenUtil;
import org.jetbrains.annotations.NotNull;
import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.ICustomComponent;
import vazkii.patchouli.api.IVariable;

import java.util.List;
import java.util.function.UnaryOperator;

import static network.something.somepotter.mechanics.gesture.GestureHud.LINE_COLORS;

public class WandMovementComponent implements ICustomComponent {

    public transient String spellId = "";
    public transient List<Point> points;
    public transient float ticksPassed = 0;

    @Override
    public void build(int componentX, int componentY, int pageNum) {
    }

    @Override
    public void render(PoseStack ms, IComponentRenderContext context, float ticks, int mouseX, int mouseY) {
        if (!SpellInit.has(spellId)) return;

//        GuiComponent.fill(ms, startX, startY, endX, endY, 0xFF00FFFF);

        ticksPassed += ticks;
        var progress = ticksPassed / (20 * 2F);
        if (progress > 1.5) {
            ticksPassed = 0;
        }

        drawPoints(ms, progress);

    }

    protected void drawPoints(PoseStack stack, float progress) {
        if (points.size() < 2) return; // No points or only one point to draw

        var point1 = points.get(0);
        int currentStrokeId = point1.StrokeID;
        int color = getLineColor(currentStrokeId);

        var pointsToDraw = Math.min((int) (points.size() * progress), points.size());

        for (int i = 1; i < pointsToDraw; i++) {
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

    protected int getLineColor(int strokeId) {
        return LINE_COLORS.get(strokeId % LINE_COLORS.size());
    }

    @Override
    public void onVariablesAvailable(@NotNull UnaryOperator<IVariable> lookup) {
        spellId = lookup.apply(IVariable.wrap("#spell_id#")).asString();
        var spell = SpellInit.get(spellId);
        var gesture = spell.getGestures().get(0);

        points = gesture.getPoints();
    }
}
