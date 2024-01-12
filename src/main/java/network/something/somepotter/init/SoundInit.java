package network.something.somepotter.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import network.something.somepotter.SomePotter;

public class SoundInit {

    protected static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SomePotter.MOD_ID);

    public static final RegistryObject<SoundEvent> CAST = registerSoundEvent("cast");


    protected static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(
                new ResourceLocation(SomePotter.MOD_ID, name)
        ));
    }

    public static void register(IEventBus bus) {
        SOUND_EVENTS.register(bus);
    }

}
