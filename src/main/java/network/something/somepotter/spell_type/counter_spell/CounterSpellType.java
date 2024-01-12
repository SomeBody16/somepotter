package network.something.somepotter.spell_type.counter_spell;

import network.something.somepotter.spell_type.SpellType;
import network.something.somepotter.util.ColorUtil;

public class CounterSpellType extends SpellType {
    public static String ID = "counter_spell";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public ColorUtil getColor() {
        return ColorUtil.COUNTER_SPELL;
    }
}
