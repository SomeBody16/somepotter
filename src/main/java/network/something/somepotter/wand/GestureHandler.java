package network.something.somepotter.wand;

import com.github.cluelab.dollar.Gesture;
import com.github.cluelab.dollar.Point;
import com.github.cluelab.dollar.PointCloudRecognizer;
import network.something.somepotter.SomePotter;
import network.something.somepotter.spell.basic_cast.BasicCastSpell;

import java.util.ArrayList;
import java.util.List;

public class GestureHandler {
    private static final List<Gesture> trainingSet = new ArrayList<>();

    public static void registerGesture(String name, Point[] points) {
        SomePotter.LOGGER.info("Registered gesture: {} ({} points)", name, points.length);
        var gesture = new Gesture(points, name);
        trainingSet.add(gesture);
    }


    private final List<Point> recordedPoints = new ArrayList<>();

    private int strokeId = 0;
    private boolean recording;

    public GestureHandler() {
        recording = false;
    }

    public void initRecording() {
        SomePotter.LOGGER.info("Started recording");
        recordedPoints.clear();
        strokeId = 0;
        recording = false;
    }

    public void startRecording() {
        recording = true;
    }

    public void pauseRecording() {
        recording = false;
    }

    public void recordPoint(float x, float y) {
        if (recording) {
            SomePotter.LOGGER.info("Recording point: ({}, {})", x, y);
            var lastPoint = getLastPoint();
            if (lastPoint.X == x && lastPoint.Y == y) return;

            var point = new Point(x, y, strokeId);
            recordedPoints.add(point);
        }
    }

    public void stopRecordingAndRecognize() {
        if (!recording) return;
        SomePotter.LOGGER.info("Stopped recording");

        try {
            recording = false;

            var candidate = new Gesture(getRecordedPoints());

            var spell = PointCloudRecognizer.Classify(candidate, getTrainingSet());
//        var spell = PointCloudRecognizerPlus.Classify(candidate, getTrainingSet());
//        var spell = QPointCloudRecognizer.Classify(candidate, getTrainingSet());

            SomePotter.LOGGER.info("Sending spell packet: " + spell);
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
        SomePotter.LOGGER.info("Next stroke");
        strokeId++;
    }

    public Point[] getRecordedPoints() {
        return recordedPoints.toArray(new Point[0]);
    }

    private Gesture[] getTrainingSet() {
        return trainingSet.toArray(new Gesture[0]);
    }

    private Point getLastPoint() {
        if (recordedPoints.isEmpty()) return new Point(0, 0, 0);
        return recordedPoints.get(recordedPoints.size() - 1);
    }
}
