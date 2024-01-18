package network.something.somepotter.datagen.patchouli;

import com.google.common.hash.HashFunction;
import com.google.gson.JsonArray;
import net.minecraft.data.HashCache;

import java.io.IOException;
import java.nio.file.Path;

public class BookPageTemplate extends BookObject {

    public static class Component extends BookObject {
        public Component() {
            super("");
        }
    }

    protected JsonArray components = new JsonArray();

    public BookPageTemplate(String id) {
        super(id);
    }

    public void addComponent(Component component) {
        components.add(component.json);
    }

    public void save(Path bookPath, HashCache cache, HashFunction hash) throws IOException {
        json.add("components", components);
        Path entryPath = bookPath.resolve("en_us/templates/%s.json".formatted(id));
        write(cache, hash, entryPath, json);
    }
}
