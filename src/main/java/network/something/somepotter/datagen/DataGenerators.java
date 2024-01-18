package network.something.somepotter.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import network.something.somepotter.SomePotter;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        var fileHelper = event.getExistingFileHelper();

        generator.addProvider(new SpellBookProvider(generator));
        generator.addProvider(new SpellAdvancementProvider(generator, fileHelper));
    }

}
