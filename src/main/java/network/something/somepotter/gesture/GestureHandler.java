package network.something.somepotter.gesture;

import com.github.cluelab.dollar.Gesture;
import com.github.cluelab.dollar.Point;
import com.github.cluelab.dollar.PointCloudRecognizer;
import network.something.somepotter.SomePotter;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.spell.basic_cast.BasicCastSpell;
import network.something.somepotter.wand.SpellChosenPacket;

import java.util.ArrayList;
import java.util.List;

public class GestureHandler {

    private final List<Point> recordedPoints = new ArrayList<>();

    private int strokeId = 0;
    private boolean recording;

    public GestureHandler() {
        recording = false;
    }

    public void initRecording() {
        recordedPoints.clear();
        strokeId = 0;
        recording = false;
    }

    public void startRecording() {
        recording = true;
    }

    public boolean isRecording() {
        return recording;
    }

    public void recordPoint(float x, float y) {
        if (recording) {
            var lastPoint = getLastPoint();
            if (lastPoint.X == x && lastPoint.Y == y) return;

            var point = new Point(x, y, strokeId);
            recordedPoints.add(point);
        }
    }

    public void stopRecordingAndRecognize() {
        if (!recording) return;

        try {
            recording = false;

            var candidate = new Gesture(getRecordedPoints());

            var spell = PointCloudRecognizer.Classify(candidate, getTrainingSet());
//        var spell = PointCloudRecognizerPlus.Classify(candidate, getTrainingSet());
//        var spell = QPointCloudRecognizer.Classify(candidate, getTrainingSet());

            var packet = new SpellChosenPacket(spell);
            packet.sendToServer();
        } catch (Exception e) {
            SomePotter.LOGGER.error("Error recognizing gesture", e);
            var packet = new SpellChosenPacket(BasicCastSpell.ID);
            packet.sendToServer();
        }

        recordedPoints.clear();
    }

    public void nextStroke() {
        strokeId++;
    }

    public Point[] getRecordedPoints() {
        return recordedPoints.toArray(new Point[0]);
    }

    private Gesture[] getTrainingSet() {
        var result = new ArrayList<Gesture>();

        for (var spell : SpellInit.all()) {
            for (var wrapper : spell.getGestures()) {
                result.add(wrapper.getGesture());
            }
        }

        return result.toArray(new Gesture[0]);
    }

    private Point getLastPoint() {
        if (recordedPoints.isEmpty()) return new Point(0, 0, 0);
        return recordedPoints.get(recordedPoints.size() - 1);
    }
}
