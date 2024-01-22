package network.something.somepotter.documentation.content.wiki.entry;

import network.something.somepotter.documentation.helper.patchouli.BookCategory;
import network.something.somepotter.documentation.helper.patchouli.BookEntry;
import network.something.somepotter.documentation.helper.patchouli.BookPage;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.spells.spell.protego_maxima.ProtegoMaximaSpell;

public class ProtegoMaximaEntry {

    public static void create(BookCategory root) {
        var entry = BookEntry.translated("protego_maxima", root, "spell_book.wiki")
                .setIcon(SpellInit.get(ProtegoMaximaSpell.ID).getIcon());

        var intro = BookPage.translated(entry, "spell_book.wiki", "intro");
        entry.addPage(intro);

        var claiming = BookPage.translated(entry, "spell_book.wiki", "claiming");
        claiming.addProperty("title", "spell_book.wiki.protego_maxima.claiming.title");
        entry.addPage(claiming);

        var whitelist = BookPage.translated(entry, "spell_book.wiki", "whitelist");
        whitelist.addProperty("title", "spell_book.wiki.protego_maxima.whitelist.title");
        entry.addPage(whitelist);

        root.addEntry(entry);
    }

}
