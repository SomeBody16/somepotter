package network.something.somepotter.spell_type.curse;

import network.something.somepotter.spell_type.SpellType;
import network.something.somepotter.util.ColorUtil;

public class CurseType extends SpellType {
    public static String ID = "curse";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public ColorUtil getColor() {
        return ColorUtil.CURSE;
    }
}
