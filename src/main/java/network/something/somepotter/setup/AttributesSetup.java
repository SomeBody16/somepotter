package network.something.somepotter.setup;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.init.EntityInit;
import network.something.somepotter.spell.spells.morsmordre.dark_mark.DarkMarkEntity;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttributesSetup {

    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityInit.DARK_MARK.get(), DarkMarkEntity.setAttributes());
    }
}
