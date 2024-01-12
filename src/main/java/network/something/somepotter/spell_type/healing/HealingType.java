package network.something.somepotter.spell_type.healing;

import network.something.somepotter.spell_type.SpellType;
import network.something.somepotter.util.ColorUtil;

public class HealingType extends SpellType {
    public static String ID = "healing";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public ColorUtil getColor() {
        return ColorUtil.HEALING;
    }
}
