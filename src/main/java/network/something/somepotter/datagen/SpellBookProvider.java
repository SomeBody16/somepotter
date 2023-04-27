package network.something.somepotter.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import network.something.somepotter.datagen.patchouli.*;
import network.something.somepotter.spell.spells.Spells;
import network.something.somepotter.spell.type.SpellTypes;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class SpellBookProvider implements DataProvider {
    protected final DataGenerator generator;
    protected final BookGenerator book;

    protected BookPageTemplate spellSummaryTemplate;

    public SpellBookProvider(DataGenerator generator) {
        this.generator = generator;

        book = new BookGenerator("wizard_guide");
        book.addProperty("name", "spell_book.name");
        book.addProperty("landing_text", "spell_book.landing_text");
        book.addProperty("version", 1);
        book.addProperty("show_progress", false);
        book.addProperty("show_toasts", false);
        book.addProperty("i18n", true);
    }

    @Override
    public void run(@NotNull HashCache cache) throws IOException {
        spellTypes();
        spells();

        var dataPath = generator.getOutputFolder().resolve("data");
        book.save(dataPath, cache, SHA1);
    }

    protected void spells() {
        createSpellSummaryTemplate();
        var category = BookCategory.translated("spells", "wizard_guide.category");

        for (var spell : Spells.asList()) {
            var id = spell.getId();
            var entry = BookEntry.translated(id, category, "spell");

            var summary = new BookPage();
            summary.addProperty("type", "patchouli:spell_summary");
            summary.addProperty("spell_id", id);
            summary.addProperty("spell", "spell." + id);
            summary.addProperty("spell_type", "spell.type." + spell.getType().getId());
            summary.addProperty("cast_type", "cast");
            summary.addProperty("description", "spell." + id + ".description");
            entry.addPage(summary);

            entry.addPage(BookPage.translated(entry, "spell", "page2"));

            category.addEntry(entry);
        }

        book.addCategory(category);
    }

    protected void spellTypes() {
        var category = BookCategory.translated("spell_types", "wizard_guide.category");

        for (var spellType : SpellTypes.asList()) {
            var id = spellType.getId();
            var entry = BookEntry.translated(id, category, "spell.type");

            entry.addPage(BookPage.translated(entry, "spell.type", "page1"));
            entry.addPage(BookPage.translated(entry, "spell.type", "page2"));

            category.addEntry(entry);
        }

        book.addCategory(category);
    }

    protected void createSpellSummaryTemplate() {
        spellSummaryTemplate = new BookPageTemplate("spell_summary");

        var header = new BookPageTemplate.Component();
        header.addProperty("type", "patchouli:header");
        header.addProperty("text", "#spell#");
        header.addProperty("centered", false);
        spellSummaryTemplate.addComponent(header);

        var typeLabel = new BookPageTemplate.Component();
        typeLabel.addProperty("type", "patchouli:text");
        typeLabel.addProperty("text", "$(bold)Type:");
        typeLabel.addProperty("y", 12);
        spellSummaryTemplate.addComponent(typeLabel);

        var typeValue = new BookPageTemplate.Component();
        typeValue.addProperty("type", "patchouli:text");
        typeValue.addProperty("text", "#spell_type#");
        typeValue.addProperty("y", 12);
        typeValue.addProperty("x", 30);
        spellSummaryTemplate.addComponent(typeValue);

        var castLabel = new BookPageTemplate.Component();
        castLabel.addProperty("type", "patchouli:text");
        castLabel.addProperty("text", "$(bold)Cast:");
        castLabel.addProperty("y", 24);
        spellSummaryTemplate.addComponent(castLabel);

        var castValue = new BookPageTemplate.Component();
        castValue.addProperty("type", "patchouli:text");
        castValue.addProperty("text", "#cast_type#");
        castValue.addProperty("y", 24);
        castValue.addProperty("x", 30);
        spellSummaryTemplate.addComponent(castValue);

        var description = new BookPageTemplate.Component();
        description.addProperty("type", "patchouli:text");
        description.addProperty("text", "$(italic)   #description#");
        description.addProperty("y", 40);
        spellSummaryTemplate.addComponent(description);

        var selectButton = new BookPageTemplate.Component();
        selectButton.addProperty("type", "patchouli:text");
        selectButton.addProperty("text", "$(c:/somepotter set_spell #spell_id#)Select$()");
        selectButton.addProperty("x", 10);
        selectButton.addProperty("y", 130);
        spellSummaryTemplate.addComponent(selectButton);

        book.addTemplate(spellSummaryTemplate);
    }


    @Override
    public @NotNull String getName() {
        return "Spell Book";
    }
}
