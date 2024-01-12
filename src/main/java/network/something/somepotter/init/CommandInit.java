package network.something.somepotter.init;

import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.commands.synchronization.EmptyArgumentSerializer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.command.SpellArgument;
import network.something.somepotter.command.SpellCommand;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID)
public class CommandInit {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        var dispatcher = event.getDispatcher();

        if (!ArgumentTypes.isTypeRegistered(SpellArgument.spell())) {
            ArgumentTypes.register("spell", SpellArgument.class, new EmptyArgumentSerializer<>(SpellArgument::spell));
        }

        SpellCommand.register(dispatcher);
    }

}
