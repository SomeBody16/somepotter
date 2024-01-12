package network.something.somepotter.spell_type.jinx;

import network.something.somepotter.spell_type.SpellType;
import network.something.somepotter.util.ColorUtil;

public class JinxType extends SpellType {
    public static String ID = "jinx";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public ColorUtil getColor() {
        return ColorUtil.JINX;
    }
}
