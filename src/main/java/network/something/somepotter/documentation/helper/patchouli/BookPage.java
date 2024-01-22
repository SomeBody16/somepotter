package network.something.somepotter.documentation.helper.patchouli;

import com.google.gson.JsonObject;

public class BookPage extends BookObject {

    public static BookPage translated(BookEntry entry, String i18nBase, String pageId) {
        var id = entry.id;
        var page = new BookPage();

        page.addProperty("type", "patchouli:text");
        page.addProperty("text", i18nBase + "." + id + "." + pageId);

        return page;
    }

    public static BookPage recipe(String recipeId) {
        var page = new BookPage();

        page.addProperty("type", "patchouli:crafting");
        page.addProperty("recipe", recipeId);

        return page;
    }

    public BookPage() {
        super("");
    }

    JsonObject getJson() {
        return json;
    }
}
