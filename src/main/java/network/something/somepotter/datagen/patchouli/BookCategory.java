package network.something.somepotter.datagen.patchouli;

import com.google.common.hash.HashFunction;
import net.minecraft.data.HashCache;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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

    public BookCategory setIcon(ItemStack icon) {
        var str = icon.getItem().getRegistryName().toString();
        str += icon.hasTag() ? icon.getTag().toString() : "";
        addProperty("icon", str);
        return this;
    }

    public BookCategory setIcon(Item icon) {
        return setIcon(new ItemStack(icon));
    }

    public BookCategory setParent(BookCategory parent) {
        addProperty("parent", SomePotter.MOD_ID + ":" + parent.id);
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

        var entriesPath = bookPath.resolve("en_us/entries/" + id);
        for (var entry : entries) {
            entry.save(entriesPath, cache, hash);
        }
    }
}
