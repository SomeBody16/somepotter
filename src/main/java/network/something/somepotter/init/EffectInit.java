package network.something.somepotter.init;

import net.minecraftforge.eventbus.api.IEventBus;
import network.something.somepotter.spell.effect.SpellEffects;

public class EffectInit {

    public static void register(IEventBus eventBus) {
        SpellEffects.register(eventBus);
    }

}
