package network.something.somemagic.magic;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somemagic.SomeMagic;
import network.something.somemagic.magic.effect.core.SpellEffect;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = SomeMagic.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpellEffectTick {

    private static final List<SpellEffect> EFFECTS = new ArrayList<>();

    public static void addEffect(SpellEffect effect) {
        EFFECTS.add(effect);
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        EFFECTS.forEach(SpellEffect::tick);
        EFFECTS.removeIf(SpellEffect::isExpired);
    }

}
