package network.something.somepotter.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import network.something.somepotter.datagen.patchouli.*;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.spell.basic_cast.BasicCastSpell;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class SpellBookProvider implements DataProvider {
    protected final DataGenerator generator;
    protected final BookGenerator book;

    protected BookPageTemplate spellSummaryTemplate;

    public SpellBookProvider(DataGenerator generator) {
        this.generator = generator;

        book = new BookGenerator("spell_book");
        book.addProperty("name", "spell_book");
        book.addProperty("landing_text", "spell_book.landing_text");
        book.addProperty("version", 1);
        book.addProperty("show_progress", false);
        book.addProperty("show_toasts", false);
        book.addProperty("i18n", true);
        book.addProperty("creative_tab", "minecraft:tools_and_utilities");
        book.addProperty("use_resource_pack", true);
    }

    @Override
    public void run(@NotNull HashCache cache) throws IOException {
        createSpellSummaryTemplate();
        createWandMovementTemplate();

//        spellTypes();
        spells();

        var dataPath = generator.getOutputFolder().resolve("data");
        var assetsPath = generator.getOutputFolder().resolve("assets");
        book.save(dataPath, assetsPath, cache, SHA1);
    }

    protected void spells() {
        var category = BookCategory.translated("spells", "spell_book.category")
                .setIcon("minecraft:enchanted_book");

        for (var spell : SpellInit.allSpells()) {
            var id = spell.getId();
            if (Objects.equals(id, BasicCastSpell.ID)) continue;

            var entry = BookEntry.translated(id, category, "spell")
                    .setIcon("minecraft:enchanted_book");

            var summary = new BookPage();
            summary.addProperty("type", "somepotter:spell_summary");
            summary.addProperty("spell_id", id);
            summary.addProperty("spell", "spell." + id);
//            summary.addProperty("spell_type", "spell.type." + spell.getType().getId());
            summary.addProperty("cast_type", "spell.cast." + spell.getCast().getId());
            summary.addProperty("description", "spell." + id + ".description");
            entry.addPage(summary);

            var wandMovement = new BookPage();
            wandMovement.addProperty("type", "somepotter:wand_movement");
            wandMovement.addProperty("spell_id", id);
            entry.addPage(wandMovement);

            entry.addPage(BookPage.translated(entry, "spell", "page2"));

            category.addEntry(entry);
        }

        book.addCategory(category);
    }

//    protected void spellTypes() {
//        var category = BookCategory.translated("spell_types", "spell_book.category");
//
//        for (var spellType : SpellTypes.asList()) {
//            var id = spellType.getId();
//            var entry = BookEntry.translated(id, category, "spell.type");
//
//            entry.addPage(BookPage.translated(entry, "spell.type", "page1"));
//            entry.addPage(BookPage.translated(entry, "spell.type", "page2"));
//
//            category.addEntry(entry);
//        }
//
//        book.addCategory(category);
//    }

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

        book.addTemplate(spellSummaryTemplate);
    }

    protected void createWandMovementTemplate() {
        spellSummaryTemplate = new BookPageTemplate("wand_movement");

        var frame = new BookPageTemplate.Component();
        frame.addProperty("type", "patchouli:frame");
        spellSummaryTemplate.addComponent(frame);

        var wandMovement = new BookPageTemplate.Component();
        wandMovement.addProperty("type", "patchouli:custom");
        wandMovement.addProperty("class", "network.something.somepotter.patchouli.WandMovementComponent");
        spellSummaryTemplate.addComponent(wandMovement);

        book.addTemplate(spellSummaryTemplate);
    }

    @Override
    public @NotNull String getName() {
        return "Spell Book";
    }
}
