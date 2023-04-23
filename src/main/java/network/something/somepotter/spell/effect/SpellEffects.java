package network.something.somepotter.spell.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import network.something.somepotter.SomePotter;
import network.something.somepotter.spell.spells.crucio.CrucioEffect;
import network.something.somepotter.spell.spells.crucio.CrucioSpell;

public class SpellEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SomePotter.MOD_ID);

    public static final RegistryObject<MobEffect> CRUCIO =
            SpellEffects.EFFECTS.register(CrucioSpell.ID, CrucioEffect::new);

    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }

}
