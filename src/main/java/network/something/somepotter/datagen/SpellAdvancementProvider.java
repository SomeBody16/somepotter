package network.something.somepotter.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import network.something.somepotter.SomePotter;
import network.something.somepotter.init.SpellInit;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class SpellAdvancementProvider extends AdvancementProvider {
    public SpellAdvancementProvider(DataGenerator generatorIn, ExistingFileHelper fileHelperIn) {
        super(generatorIn, fileHelperIn);
    }

    @Override
    protected void registerAdvancements(@NotNull Consumer<Advancement> consumer, @NotNull ExistingFileHelper fileHelper) {

        var rootId = new ResourceLocation(SomePotter.MOD_ID, "spell/root").toString();
        var root = Advancement.Builder.advancement()
                .addCriterion("root", new ImpossibleTrigger.TriggerInstance())
                .save(consumer, rootId);

        for (var spellId : SpellInit.allForDocs().keySet()) {

            var id = new ResourceLocation(SomePotter.MOD_ID, "spell/" + spellId).toString();
            Advancement.Builder.advancement()
                    .parent(root)
                    .addCriterion("learned", new ImpossibleTrigger.TriggerInstance())
                    .save(consumer, id);
        }

    }
}
