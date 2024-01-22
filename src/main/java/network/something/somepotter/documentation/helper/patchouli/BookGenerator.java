package network.something.somepotter.documentation.helper.patchouli;

import com.google.common.hash.HashFunction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.data.HashCache;
import network.something.somepotter.SomePotter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BookGenerator extends BookObject {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();

    protected List<BookCategory> categories = new ArrayList<>();
    protected List<BookPageTemplate> templates = new ArrayList<>();

    public BookGenerator(String id) {
        super(id);
    }

    public void addCategory(BookCategory category) {
        categories.add(category);
    }

    public void addTemplate(BookPageTemplate template) {
        templates.add(template);
    }

    public void save(Path dataPath, Path assetsPath, HashCache cache, HashFunction hash) throws IOException {
        Path bookPath = dataPath
                .resolve(SomePotter.MOD_ID)
                .resolve("patchouli_books")
                .resolve(id);

        Path bookJsonPath = bookPath.resolve("book.json");
        write(cache, hash, bookJsonPath, json);

        Path bookAssetPath = assetsPath
                .resolve(SomePotter.MOD_ID)
                .resolve("patchouli_books")
                .resolve(id);

        for (var category : categories) {
            category.save(bookAssetPath, cache, hash);
        }

        for (var template : templates) {
            template.save(bookAssetPath, cache, hash);
        }
    }
}
