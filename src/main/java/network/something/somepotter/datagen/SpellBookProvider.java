package network.something.somepotter.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.world.item.Items;
import network.something.somepotter.datagen.patchouli.*;
import network.something.somepotter.init.ItemInit;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.mechanics.spell_learn.SpellLearnManager;
import network.something.somepotter.spells.spell.basic_cast.BasicCastSpell;
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
        book.addProperty("use_resource_pack", true);
        book.addProperty("creative_tab", "somepotter:somepotter");
        book.addProperty("model", "patchouli:book_green");
        book.addProperty("book_texture", "patchouli:textures/gui/book_green.png");
    }

    @Override
    public void run(@NotNull HashCache cache) throws IOException {
        createSpellSummaryTemplate();
        createWandMovementTemplate();
        createRequirementsTemplate();

        spells();
        wiki();

        var dataPath = generator.getOutputFolder().resolve("data");
        var assetsPath = generator.getOutputFolder().resolve("assets");
        book.save(dataPath, assetsPath, cache, SHA1);
    }

    protected void spells() {
        var root = BookCategory.translated("spells", "spell_book.category")
                .setIcon(ItemInit.WAND.get());

        for (var spellType : SpellTypeInit.all()) {
            var category = BookCategory.translated(spellType.getId(), "spell.type")
                    .setParent(root)
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

                var summary = new BookPage();
                summary.addProperty("type", "somepotter:spell_summary");
                summary.addProperty("spell_id", spellId);
                summary.addProperty("spell", "spell." + spellId);
                summary.addProperty("spell_type", "spell.type." + spell.getType().getId());
                summary.addProperty("cast_type", "spell.cast." + spell.getCast().getId());
                summary.addProperty("description", "spell." + spellId + ".description");
                entry.addPage(summary);

                var wandMovement = new BookPage();
                wandMovement.addProperty("type", "somepotter:wand_movement");
                wandMovement.addProperty("spell_id", spellId);
                wandMovement.addProperty("advancement", SpellLearnManager.getAdvancementId(spell).toString());
                entry.addPage(wandMovement);

                var page2 = BookPage.translated(entry, "spell", "page2");
                page2.addProperty("advancement", SpellLearnManager.getAdvancementId(spell).toString());
                entry.addPage(page2);

                var requirements = new BookPage();
                requirements.addProperty("type", "somepotter:spell_requirements");
                requirements.addProperty("spell_id", spellId);
                entry.addPage(requirements);

                category.addEntry(entry);
            }

            book.addCategory(category);
        }

        book.addCategory(root);
    }

    protected void wiki() {
        var root = BookCategory.translated("wiki", "spell_book.category")
                .setIcon(Items.KNOWLEDGE_BOOK);

        // Experience Vanilla
        var experienceVanilla = BookEntry.translated("experience_vanilla", root, "spell_book.wiki")
                .setIcon(Items.EXPERIENCE_BOTTLE);
        experienceVanilla.addProperty("flag", "!mod:the_vault");

        var pageVanilla1 = BookPage.translated(experienceVanilla, "spell_book.wiki", "experience_vanilla");
        pageVanilla1.addProperty("type", "patchouli:text");
        pageVanilla1.addProperty("text", "spell_book.wiki.experience_vanilla.page1");
        experienceVanilla.addPage(pageVanilla1);

        var pageVanilla2 = BookPage.translated(experienceVanilla, "spell_book.wiki", "experience_vanilla");
        pageVanilla2.addProperty("type", "patchouli:text");
        pageVanilla2.addProperty("text", "spell_book.wiki.experience_vanilla.page2");
        experienceVanilla.addPage(pageVanilla2);

        root.addEntry(experienceVanilla);

        // Experience The Vault
        var experienceTheVault = BookEntry.translated("experience_the_vault", root, "spell_book.wiki")
                .setIcon(Items.EXPERIENCE_BOTTLE);
        experienceTheVault.addProperty("flag", "mod:the_vault");

        var pageTheVault1 = BookPage.translated(experienceTheVault, "spell_book.wiki", ".experience_the_vault.page1");
        experienceTheVault.addPage(pageTheVault1);

        var pageTheVault2 = BookPage.translated(experienceTheVault, "spell_book.wiki", "experience_the_vault.page2");
        experienceTheVault.addPage(pageTheVault2);

        root.addEntry(experienceTheVault);

        // Floo Network
        var flooNetwork = BookEntry.translated("floo_network", root, "spell_book.wiki")
                .setIcon(ItemInit.FLOO_POWDER.get());

        var floo1 = BookPage.translated(flooNetwork, "spell_book.wiki", "1");
        flooNetwork.addPage(floo1);

        var floo2 = BookPage.translated(flooNetwork, "spell_book.wiki", "2");
        flooNetwork.addPage(floo2);

        root.addEntry(flooNetwork);


        book.addCategory(root);
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
        description.addProperty("text", "#description#");
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
        wandMovement.addProperty("class", "network.something.somepotter.integration.patchouli.WandMovementComponent");
        spellSummaryTemplate.addComponent(wandMovement);

        book.addTemplate(spellSummaryTemplate);
    }

    protected void createRequirementsTemplate() {
        spellSummaryTemplate = new BookPageTemplate("spell_requirements");

        var wandMovement = new BookPageTemplate.Component();
        wandMovement.addProperty("type", "patchouli:custom");
        wandMovement.addProperty("class", "network.something.somepotter.integration.patchouli.SpellRequirementsComponent");
        spellSummaryTemplate.addComponent(wandMovement);

        book.addTemplate(spellSummaryTemplate);
    }

    @Override
    public @NotNull String getName() {
        return "Spell Book";
    }
}
