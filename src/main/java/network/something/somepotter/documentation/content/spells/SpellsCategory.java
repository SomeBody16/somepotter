package network.something.somepotter.documentation.content.spells;

import network.something.somepotter.documentation.helper.patchouli.BookCategory;
import network.something.somepotter.documentation.helper.patchouli.BookEntry;
import network.something.somepotter.documentation.helper.patchouli.BookGenerator;
import network.something.somepotter.documentation.helper.patchouli.BookPage;
import network.something.somepotter.documentation.template.requirements.RequirementsTemplate;
import network.something.somepotter.documentation.template.spell_summary.SpellSummaryTemplate;
import network.something.somepotter.documentation.template.wand_movement.WandMovementTemplate;
import network.something.somepotter.init.ItemInit;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.mechanics.spell_learn.SpellLearnManager;
import network.something.somepotter.spells.spell.basic_cast.BasicCastSpell;

import java.util.Objects;

public class SpellsCategory extends BookCategory {

    public static void create(BookGenerator book) {
        book.addCategory(new SpellsCategory(book));
    }

    protected SpellsCategory(BookGenerator book) {
        super("spells");

        addProperty("name", "spell_book.spells");
        addProperty("description", "spell_book.spells.description");
        setIcon(ItemInit.WAND.get());

        for (var spellType : SpellTypeInit.all()) {
            var category = BookCategory.translated(spellType.getId(), "spell.type")
                    .setParent(this)
                    .setIcon(spellType.getIcon());


            for (var spellId : SpellInit.allForDocs().keySet()) {
                if (Objects.equals(spellId, BasicCastSpell.ID)) continue;
                var spell = SpellInit.get(spellId);
                if (!spell.getType().getId().equals(spellType.getId())) continue;


                var entry = BookEntry.translated(spellId, category, "spell")
                        .setIcon(spell.getIcon());

                var entryProperties = SpellInit.allForDocs().get(spellId);
                for (var property : entryProperties.keySet()) {
                    entry.addProperty(property, entryProperties.get(property));
                }

                var summary = SpellSummaryTemplate.create(spell);
                entry.addPage(summary);

                var wandMovement = WandMovementTemplate.create(spell);
                wandMovement.addProperty("advancement", SpellLearnManager.getAdvancementId(spell).toString());
                entry.addPage(wandMovement);

                var page2 = BookPage.translated(entry, "spell", "page2");
                page2.addProperty("advancement", SpellLearnManager.getAdvancementId(spell).toString());
                entry.addPage(page2);

                var requirements = RequirementsTemplate.create(spell);
                entry.addPage(requirements);

                category.addEntry(entry);
            }

            book.addCategory(category);
        }
    }

}
