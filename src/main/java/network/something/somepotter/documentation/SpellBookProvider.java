package network.something.somepotter.documentation;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import network.something.somepotter.documentation.content.spells.SpellsCategory;
import network.something.somepotter.documentation.content.wiki.WikiCategory;
import network.something.somepotter.documentation.helper.patchouli.BookGenerator;
import network.something.somepotter.documentation.template.requirements.RequirementsTemplate;
import network.something.somepotter.documentation.template.spell_summary.SpellSummaryTemplate;
import network.something.somepotter.documentation.template.wand_movement.WandMovementTemplate;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class SpellBookProvider implements DataProvider {
    protected final DataGenerator generator;
    protected final BookGenerator book;

    public SpellBookProvider(DataGenerator generator) {
        this.generator = generator;

        book = new BookGenerator("spell_book");
        book.addProperty("name", "spell_book");
        book.addProperty("landing_text", "spell_book.landing_text");
        book.addProperty("version", 1);
        book.addProperty("show_progress", false);
        book.addProperty("show_toasts", false);
        book.addProperty("i18n", true);
        book.addProperty("use_resource_pack", true);
        book.addProperty("creative_tab", "somepotter:somepotter");
        book.addProperty("model", "patchouli:book_green");
        book.addProperty("book_texture", "patchouli:textures/gui/book_green.png");
    }

    @Override
    public void run(@NotNull HashCache cache) throws IOException {
        SpellSummaryTemplate.register(book);
        WandMovementTemplate.register(book);
        RequirementsTemplate.register(book);

        SpellsCategory.create(book);
        WikiCategory.create(book);

        var dataPath = generator.getOutputFolder().resolve("data");
        var assetsPath = generator.getOutputFolder().resolve("assets");
        book.save(dataPath, assetsPath, cache, SHA1);
    }

    @Override
    public @NotNull String getName() {
        return "Spell Book";
    }
}
