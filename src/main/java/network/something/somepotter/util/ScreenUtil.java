package network.something.somepotter.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ScreenUtil {

    public static List<Point> getLinePoints(int x1, int y1, int x2, int y2) {
        var result = new ArrayList<Point>();

        // Simple line drawing algorithm (can be replaced with a more efficient one)
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            result.add(new Point(x1, y1));

            if (x1 == x2 && y1 == y2) {
                break;
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }

            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }

        return result;
    }

    public static void drawLine(PoseStack stack, int x1, int y1, int x2, int y2, int color) {
        for (var point : getLinePoints(x1, y1, x2, y2)) {
            GuiComponent.fill(stack, point.x, point.y, point.x + 1, point.y + 1, color);
        }
    }

}
