package network.something.somepotter.datagen.patchouli;

import com.google.common.hash.HashFunction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.data.HashCache;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BookObject {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();

    protected final String id;

    public BookObject(String id) {
        this.id = id;
    }

    protected JsonObject json = new JsonObject();

    public void addProperty(String name, String value) {
        json.addProperty(name, value);
    }

    public void addProperty(String name, Number value) {
        json.addProperty(name, value);
    }

    public void addProperty(String name, Boolean value) {
        json.addProperty(name, value);
    }

    protected void write(HashCache cache, HashFunction hash, Path path, JsonElement json) throws IOException {
        var str = GSON.toJson(json);
        var strHash = hash.hashUnencodedChars(str).toString();

//        if (Files.exists(path) && Objects.equals(cache.getHash(path), strHash)) {
//            return;
//        }

        Files.createDirectories(path.getParent());

        var writer = Files.newBufferedWriter(path);
        try {
            writer.write(str);
        } catch (Throwable e) {
            try {
                writer.close();
            } catch (Throwable throwable) {
                e.addSuppressed(throwable);
            }
            throw e;
        }

        writer.close();
        cache.putNew(path, strHash);
    }
}
