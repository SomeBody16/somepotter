package network.something.somepotter.util;

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

}
