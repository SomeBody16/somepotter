package network.something.somepotter.datagen.patchouli;

import com.google.common.hash.HashFunction;
import net.minecraft.data.HashCache;
import network.something.somepotter.SomePotter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BookCategory extends BookObject {

    public static BookCategory translated(String id, String i18nBase) {
        var category = new BookCategory(id);

        category.addProperty("name", i18nBase + "." + id);
        category.addProperty("description", i18nBase + "." + id + ".description");
//        category.addProperty("icon", i18nBase + "." + id + ".icon");

        return category;
    }

    protected List<BookEntry> entries = new ArrayList<>();

    public BookCategory(String id) {
        super(id);
    }

    public BookCategory setIcon(String icon) {
        addProperty("icon", icon);
        return this;
    }

    public void addEntry(BookEntry entry) {
        entries.add(entry);
    }

    void save(Path bookPath, HashCache cache, HashFunction hash) throws IOException {
        var categoryPath = bookPath
                .resolve("en_us/categories")
                .resolve(id + ".json");
        write(cache, hash, categoryPath, json);

        SomePotter.LOGGER.info("Saving {} entries for {} category", entries.size(), id);
        var entriesPath = bookPath.resolve("en_us/entries/" + id);
        for (var entry : entries) {
            entry.save(entriesPath, cache, hash);
        }
    }
}
