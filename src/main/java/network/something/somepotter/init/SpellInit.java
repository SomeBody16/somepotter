package network.something.somepotter.init;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import network.something.somepotter.SomePotter;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.accio.AccioSpell;
import network.something.somepotter.spell.aguamenti.AguamentiSpell;
import network.something.somepotter.spell.basic_cast.BasicCastSpell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SpellInit {

    protected static Map<String, Spell> SPELLS = new HashMap<>();

    static {
        registerSpell(BasicCastSpell.ID, new BasicCastSpell());
        registerSpell(AccioSpell.ID, new AccioSpell());
        registerSpell(AguamentiSpell.ID, new AguamentiSpell());
    }

    protected static void registerSpell(String id, Spell spell) {
        SPELLS.put(id, spell);
    }

    public static Spell getSpell(String id) {
        if (SPELLS.containsKey(id)) {
            return SPELLS.get(id);
        } else {
            return SPELLS.get(BasicCastSpell.ID);
        }
    }

    public static List<Spell> allSpells() {
        return new ArrayList<>(SPELLS.values());
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        allSpells().forEach(Spell::register);
    }

}
