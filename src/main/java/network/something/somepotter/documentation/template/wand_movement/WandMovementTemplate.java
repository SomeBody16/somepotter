package network.something.somepotter.documentation.template.wand_movement;

import network.something.somepotter.documentation.helper.patchouli.BookGenerator;
import network.something.somepotter.documentation.helper.patchouli.BookPage;
import network.something.somepotter.documentation.helper.patchouli.BookPageTemplate;
import network.something.somepotter.spells.spell.Spell;

public class WandMovementTemplate {

    protected static BookPageTemplate TEMPLATE;

    public static BookPageTemplate get() {
        return TEMPLATE;
    }

    public static BookPage create(Spell spell) {
        var result = new BookPage();
        result.addProperty("type", "somepotter:wand_movement");
        result.addProperty("spell_id", spell.getId());
//        result.addProperty("advancement", SpellLearnManager.getAdvancementId(spell).toString());
        return result;
    }

    public static void register(BookGenerator book) {
        TEMPLATE = new BookPageTemplate("wand_movement");

        var frame = new BookPageTemplate.Component();
        frame.addProperty("type", "patchouli:frame");
        TEMPLATE.addComponent(frame);

        var wandMovement = new BookPageTemplate.Component();
        wandMovement.addProperty("type", "patchouli:custom");
        wandMovement.addProperty("class", "network.something.somepotter.integration.patchouli.WandMovementComponent");
        TEMPLATE.addComponent(wandMovement);

        book.addTemplate(TEMPLATE);
    }

}
