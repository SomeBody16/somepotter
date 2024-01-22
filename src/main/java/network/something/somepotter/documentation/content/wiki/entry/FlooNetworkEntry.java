package network.something.somepotter.documentation.content.wiki.entry;

import network.something.somepotter.documentation.helper.patchouli.BookCategory;
import network.something.somepotter.documentation.helper.patchouli.BookEntry;
import network.something.somepotter.documentation.helper.patchouli.BookPage;
import network.something.somepotter.init.ItemInit;

public class FlooNetworkEntry {

    public static void create(BookCategory root) {
        var flooNetwork = BookEntry.translated("floo_network", root, "spell_book.wiki")
                .setIcon(ItemInit.FLOO_POWDER.get());

        var intro = BookPage.translated(flooNetwork, "spell_book.wiki", "intro");
        flooNetwork.addPage(intro);

        var howToSetup = BookPage.translated(flooNetwork, "spell_book.wiki", "how_to_setup");
        howToSetup.addProperty("title", "spell_book.wiki.floo_network.how_to_setup.title");
        flooNetwork.addPage(howToSetup);

        var privacy = BookPage.translated(flooNetwork, "spell_book.wiki", "privacy");
        privacy.addProperty("title", "spell_book.wiki.floo_network.privacy.title");
        flooNetwork.addPage(privacy);

        root.addEntry(flooNetwork);
    }

}
