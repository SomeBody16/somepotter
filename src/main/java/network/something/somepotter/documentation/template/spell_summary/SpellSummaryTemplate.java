package network.something.somepotter.documentation.template.spell_summary;

import network.something.somepotter.documentation.helper.patchouli.BookGenerator;
import network.something.somepotter.documentation.helper.patchouli.BookPage;
import network.something.somepotter.documentation.helper.patchouli.BookPageTemplate;
import network.something.somepotter.spells.spell.Spell;

public class SpellSummaryTemplate {

    protected static BookPageTemplate TEMPLATE;

    public static BookPageTemplate get() {
        return TEMPLATE;
    }

    public static BookPage create(Spell spell) {
        var result = new BookPage();
        result.addProperty("type", "somepotter:spell_summary");
        result.addProperty("spell_id", spell.getId());
        result.addProperty("spell", "spell." + spell.getId());
        result.addProperty("spell_type", "spell.type." + spell.getType().getId());
        result.addProperty("cast_type", "spell.cast." + spell.getCast().getId());
        result.addProperty("description", "spell." + spell.getId() + ".description");
        return result;
    }

    public static void register(BookGenerator book) {
        TEMPLATE = new BookPageTemplate("spell_summary");

        var header = new BookPageTemplate.Component();
        header.addProperty("type", "patchouli:header");
        header.addProperty("text", "#spell#");
        header.addProperty("centered", false);
        TEMPLATE.addComponent(header);

        var typeLabel = new BookPageTemplate.Component();
        typeLabel.addProperty("type", "patchouli:text");
        typeLabel.addProperty("text", "$(bold)Type:");
        typeLabel.addProperty("y", 12);
        TEMPLATE.addComponent(typeLabel);

        var typeValue = new BookPageTemplate.Component();
        typeValue.addProperty("type", "patchouli:text");
        typeValue.addProperty("text", "#spell_type#");
        typeValue.addProperty("y", 12);
        typeValue.addProperty("x", 30);
        TEMPLATE.addComponent(typeValue);

        var castLabel = new BookPageTemplate.Component();
        castLabel.addProperty("type", "patchouli:text");
        castLabel.addProperty("text", "$(bold)Cast:");
        castLabel.addProperty("y", 24);
        TEMPLATE.addComponent(castLabel);

        var castValue = new BookPageTemplate.Component();
        castValue.addProperty("type", "patchouli:text");
        castValue.addProperty("text", "#cast_type#");
        castValue.addProperty("y", 24);
        castValue.addProperty("x", 30);
        TEMPLATE.addComponent(castValue);

        var description = new BookPageTemplate.Component();
        description.addProperty("type", "patchouli:text");
        description.addProperty("text", "#description#");
        description.addProperty("y", 40);
        TEMPLATE.addComponent(description);

        book.addTemplate(TEMPLATE);
    }

}
