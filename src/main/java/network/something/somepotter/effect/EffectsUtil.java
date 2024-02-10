package network.something.somepotter.effect;

import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

class EffectsUtil {

    public static List<Vec3> bezierCurve(Vec3 start, Vec3 cp1, Vec3 cp2, Vec3 end, int steps) {
        List<Vec3> points = new ArrayList<>();
        for (int i = 0; i <= steps; i++) {
            double t = (double) i / steps;
            double invT = 1 - t;
            double b0 = invT * invT * invT;
            double b1 = 3 * t * invT * invT;
            double b2 = 3 * t * t * invT;
            double b3 = t * t * t;

            double x = start.x * b0 + cp1.x * b1 + cp2.x * b2 + end.x * b3;
            double y = start.y * b0 + cp1.y * b1 + cp2.y * b2 + end.y * b3;
            double z = start.z * b0 + cp1.z * b1 + cp2.z * b2 + end.z * b3;

            points.add(new Vec3(x, y, z));
        }
        return points;
    }

}
