package network.something.somepotter.gesture;

import com.github.cluelab.dollar.Point;

import java.util.function.Function;

public class GestureDrawer {

    protected static int DOT_DISTANCE = 1;

    protected SpellGesture gesture;

    public GestureDrawer(SpellGesture gesture) {
        this.gesture = gesture;
    }

    public void line(int x1, int y1, int x2, int y2) {
        var dx = x2 - x1;
        var dy = y2 - y1;
        var length = Math.sqrt(dx * dx + dy * dy);
        var steps = length / DOT_DISTANCE;
        var xStep = dx / steps;
        var yStep = dy / steps;

        for (var i = 0; i < steps; i++) {
            var x = x1 + xStep * i;
            var y = y1 + yStep * i;
            gesture.addPoint(x, y);
        }
    }

    public void circle(int cx, int cy, int radius) {
        var circumference = 2 * Math.PI * radius;
        var steps = circumference / DOT_DISTANCE;

        for (var i = 0; i < steps; i++) {
            var theta = (i / steps) * 2 * Math.PI;
            var x = cx + radius * Math.cos(theta);
            var y = cy + radius * Math.sin(theta);
            gesture.addPoint(x, y);
        }
    }

    public void bezierCurve(int startX, int startY, int cp1x, int cp1y, int cp2x, int cp2y, int endX, int endY) {
        Function<Float, Point> getBezierPoint = (t) -> {
            var u = 1 - t;
            var tt = t * t;
            var uu = u * u;
            var uuu = uu * u;
            var ttt = tt * t;

            var point = new Point(0, 0, 0);
            point.X = uuu * startX;
            point.X += 3 * uu * t * cp1x;
            point.X += 3 * u * tt * cp2x;
            point.X += ttt * endX;

            point.Y = uuu * startY;
            point.Y += 3 * uu * t * cp1y;
            point.Y += 3 * u * tt * cp2y;
            point.Y += ttt * endY;

            return point;
        };

        var approxLength = Math.hypot(cp1x - startX, cp1y - startY)
                + Math.hypot(cp2x - cp1x, cp2y - cp1y)
                + Math.hypot(endX - cp2x, endY - cp2y);
        var tIncrement = (float) (DOT_DISTANCE / approxLength);

        for (var t = 0.0F; t < 1.0F; t += tIncrement) {
            var point = getBezierPoint.apply(t);
            gesture.addPoint(point.X, point.Y);
        }
    }
}
