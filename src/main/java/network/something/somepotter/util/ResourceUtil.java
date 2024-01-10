package network.something.somepotter.util;

import com.github.cluelab.dollar.Point;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import network.something.somepotter.SomePotter;

import java.io.InputStreamReader;

public class ResourceUtil {

    public static JsonObject loadJson(String filePath) {
        try {
            var fileLocation = new ResourceLocation(SomePotter.MOD_ID, filePath);
            var resource = Minecraft.getInstance().getResourceManager().getResource(fileLocation);

            Gson gson = new Gson();
            return gson.fromJson(new InputStreamReader(resource.getInputStream()), JsonObject.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load json file: " + filePath, e);
        }
    }

    public static GestureJson loadGesture(String spellId) {
        var gesture = ResourceUtil.loadJson("gestures/" + spellId + ".json");
        var name = gesture.get("name").getAsString();
        var points = gesture.getAsJsonArray("points");

        var pointArray = new Point[points.size()];
        for (int i = 0; i < points.size(); i++) {
            var point = points.get(i).getAsJsonObject();
            var x = point.get("x").getAsFloat();
            var y = point.get("y").getAsFloat();
            var strokeId = point.get("strokeId").getAsInt();
            pointArray[i] = new Point(x, y, strokeId);
        }

        return new GestureJson(name, pointArray);
    }

    public static class GestureJson {
        public String name;
        public Point[] points;

        public GestureJson(String name, Point[] points) {
            this.name = name;
            this.points = points;
        }
    }

}
