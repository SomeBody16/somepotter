package network.something.somepotter.init;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import network.something.somepotter.SomePotter;
import network.something.somepotter.spells.spell.disillusio.DisillusioEffect;
import network.something.somepotter.spells.spell.disillusio.DisillusioSpell;
import network.something.somepotter.spells.spell.engorgio.EngorgioEffect;
import network.something.somepotter.spells.spell.engorgio.EngorgioSpell;
import network.something.somepotter.spells.spell.incarcerous_captura.IncarcerousCapturaEffect;
import network.something.somepotter.spells.spell.incarcerous_captura.IncarcerousCapturaSpell;
import network.something.somepotter.spells.spell.protego.ProtegoEffect;
import network.something.somepotter.spells.spell.protego.ProtegoSpell;
import network.something.somepotter.spells.spell.reducio.ReducioEffect;
import network.something.somepotter.spells.spell.reducio.ReducioSpell;

public class EffectInit {

    protected static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SomePotter.MOD_ID);


    public static final RegistryObject<MobEffect> PROTEGO = EFFECTS.register(ProtegoSpell.ID, ProtegoEffect::new);
    public static final RegistryObject<MobEffect> DISILLUSIO = EFFECTS.register(DisillusioSpell.ID, DisillusioEffect::new);
    public static final RegistryObject<MobEffect> ENGORGIO = EFFECTS.register(EngorgioSpell.ID, EngorgioEffect::new);
    public static final RegistryObject<MobEffect> REDUCIO = EFFECTS.register(ReducioSpell.ID, ReducioEffect::new);
    public static final RegistryObject<MobEffect> INCARCEROUS_CAPTURA = EFFECTS.register(IncarcerousCapturaSpell.ID, IncarcerousCapturaEffect::new);


    public static void register(IEventBus bus) {
        EFFECTS.register(bus);
    }

}
