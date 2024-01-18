package network.something.somepotter.init;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import network.something.somepotter.SomePotter;
import network.something.somepotter.spells.spell.protego.ProtegoEffect;
import network.something.somepotter.spells.spell.protego.ProtegoSpell;

public class EffectInit {

    protected static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SomePotter.MOD_ID);


    public static final RegistryObject<MobEffect> PROTEGO = EFFECTS.register(ProtegoSpell.ID, ProtegoEffect::new);


    public static void register(IEventBus bus) {
        EFFECTS.register(bus);
    }

}
