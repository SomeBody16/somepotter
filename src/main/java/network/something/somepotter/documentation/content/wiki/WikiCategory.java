package network.something.somepotter.documentation.content.wiki;

import net.minecraft.world.item.Items;
import network.something.somepotter.documentation.content.wiki.entry.ExperienceEntry;
import network.something.somepotter.documentation.content.wiki.entry.FlooNetworkEntry;
import network.something.somepotter.documentation.content.wiki.entry.ProtegoMaximaEntry;
import network.something.somepotter.documentation.helper.patchouli.BookCategory;
import network.something.somepotter.documentation.helper.patchouli.BookGenerator;

public class WikiCategory extends BookCategory {

    public static void create(BookGenerator book) {
        book.addCategory(new WikiCategory(book));
    }

    public WikiCategory(BookGenerator book) {
        super("wiki");

        addProperty("name", "spell_book.wiki");
        addProperty("description", "spell_book.wiki.description");
        setIcon(Items.KNOWLEDGE_BOOK);

        ExperienceEntry.create(this);
        FlooNetworkEntry.create(this);
        ProtegoMaximaEntry.create(this);
    }

}
