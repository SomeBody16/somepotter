package network.something.somepotter.documentation.content.wiki.entry;

import net.minecraft.world.item.Items;
import network.something.somepotter.documentation.helper.patchouli.BookCategory;
import network.something.somepotter.documentation.helper.patchouli.BookEntry;
import network.something.somepotter.documentation.helper.patchouli.BookPage;

public class ExperienceEntry {

    public static void create(BookCategory root) {
        vanilla(root);
        the_vault(root);
    }

    protected static void vanilla(BookCategory root) {
        var entry = BookEntry.translated("experience_vanilla", root, "spell_book.wiki")
                .setIcon(Items.EXPERIENCE_BOTTLE);
        entry.addProperty("flag", "!mod:the_vault");

        var page1 = BookPage.translated(entry, "spell_book.wiki", "page1");
        entry.addPage(page1);

        var page2 = BookPage.translated(entry, "spell_book.wiki", "page2");
        entry.addPage(page2);

        root.addEntry(entry);
    }

    protected static void the_vault(BookCategory root) {
        var entry = BookEntry.translated("experience_the_vault", root, "spell_book.wiki")
                .setIcon(Items.EXPERIENCE_BOTTLE);
        entry.addProperty("flag", "mod:the_vault");

        var page1 = BookPage.translated(entry, "spell_book.wiki", "page1");
        entry.addPage(page1);

        var page2 = BookPage.translated(entry, "spell_book.wiki", "page2");
        entry.addPage(page2);

        root.addEntry(entry);
    }

}
