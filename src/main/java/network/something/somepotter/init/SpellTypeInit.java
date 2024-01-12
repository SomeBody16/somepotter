package network.something.somepotter.init;

import network.something.somepotter.spell_type.SpellType;
import network.something.somepotter.spell_type.charm.CharmType;
import network.something.somepotter.spell_type.conjuration.ConjurationType;
import network.something.somepotter.spell_type.counter_spell.CounterSpellType;
import network.something.somepotter.spell_type.curse.CurseType;
import network.something.somepotter.spell_type.healing.HealingType;
import network.something.somepotter.spell_type.jinx.JinxType;
import network.something.somepotter.spell_type.transfiguration.TransfigurationType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellTypeInit {

    protected static Map<String, SpellType> SPELL_TYPES = new HashMap<>();

    static {
        register(new CharmType());
        register(new ConjurationType());
        register(new CounterSpellType());
        register(new CurseType());
        register(new HealingType());
        register(new JinxType());
        register(new TransfigurationType());
    }

    protected static void register(SpellType spellType) {
        SPELL_TYPES.put(spellType.getId(), spellType);
    }

    public static SpellType get(String id) {
        if (SPELL_TYPES.containsKey(id)) {
            return SPELL_TYPES.get(id);
        } else {
            return SPELL_TYPES.get(CharmType.ID);
        }
    }

    public static boolean has(String id) {
        return SPELL_TYPES.containsKey(id);
    }

    public static List<SpellType> all() {
        return new ArrayList<>(SPELL_TYPES.values());
    }

}
