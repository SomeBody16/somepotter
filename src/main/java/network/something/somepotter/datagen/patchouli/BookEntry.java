package network.something.somepotter.datagen.patchouli;

import com.google.common.hash.HashFunction;
import com.google.gson.JsonArray;
import net.minecraft.data.HashCache;
import network.something.somepotter.SomePotter;

import java.io.IOException;
import java.nio.file.Path;

public class BookEntry extends BookObject {

    public static BookEntry translated(String id, BookCategory category, String i18nBase) {
        var entry = new BookEntry(id, category);

        entry.addProperty("name", i18nBase + "." + id + ".name");
        entry.addProperty("description", i18nBase + "." + id + ".description");
        entry.addProperty("icon", i18nBase + "." + id + ".icon");

        return entry;
    }

    protected JsonArray pages = new JsonArray();

    public BookEntry(String id, BookCategory category) {
        super(id);
        addProperty("category", "patchouli:" + category.id);
    }

    public void addPage(BookPage page) {
        pages.add(page.getJson());
    }

    void save(Path entriesPath, HashCache cache, HashFunction hash) throws IOException {
        SomePotter.LOGGER.info("Saving {} entry", id);
        json.add("pages", pages);
        Path entryPath = entriesPath.resolve(id + ".json");
        write(cache, hash, entryPath, json);
    }
}
