package network.something.somepotter.gesture;

import com.github.cluelab.dollar.Gesture;
import com.github.cluelab.dollar.Point;
import network.something.somepotter.spell.basic_cast.BasicCastSpell;

import java.util.ArrayList;
import java.util.List;

public class SpellGesture {

    public static SpellGesture empty() {
        return new SpellGesture(BasicCastSpell.ID);
    }


    protected List<Point> points = new ArrayList<>();
    protected int strokeId = 0;
    protected String id;
    public GestureDrawer draw;

    public SpellGesture(String id) {
        this.id = id;
        this.draw = new GestureDrawer(this);
    }

    public void addPoint(double x, double y) {
        points.add(new Point(Math.round(x), Math.round(y), strokeId));
    }

    public void nextStroke() {
        strokeId++;
    }

    public String getId() {
        return id;
    }

    public List<Point> getPoints() {
        return points;
    }

    public Gesture getGesture() {
        var points = this.points.toArray(new Point[0]);
        return new Gesture(points, id);
    }

    public void removeFromStart(int n) {
        points = points.subList(n, points.size());
    }

    public void removeFromEnd(int n) {
        points = points.subList(0, points.size() - n);
    }
}
