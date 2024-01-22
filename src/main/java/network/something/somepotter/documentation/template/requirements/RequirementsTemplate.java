package network.something.somepotter.documentation.template.requirements;

import network.something.somepotter.documentation.helper.patchouli.BookGenerator;
import network.something.somepotter.documentation.helper.patchouli.BookPage;
import network.something.somepotter.documentation.helper.patchouli.BookPageTemplate;
import network.something.somepotter.spells.spell.Spell;

public class RequirementsTemplate {

    protected static BookPageTemplate TEMPLATE;

    public static BookPageTemplate get() {
        return TEMPLATE;
    }

    public static BookPage create(Spell spell) {
        var result = new BookPage();
        result.addProperty("type", "somepotter:spell_requirements");
        result.addProperty("spell_id", spell.getId());
        return result;
    }

    public static void register(BookGenerator book) {
        TEMPLATE = new BookPageTemplate("spell_requirements");

        var wandMovement = new BookPageTemplate.Component();
        wandMovement.addProperty("type", "patchouli:custom");
        wandMovement.addProperty("class", "network.something.somepotter.integration.patchouli.SpellRequirementsComponent");
        TEMPLATE.addComponent(wandMovement);

        book.addTemplate(TEMPLATE);
    }

}
