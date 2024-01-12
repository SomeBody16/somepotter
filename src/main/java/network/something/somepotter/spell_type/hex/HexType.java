package network.something.somepotter.spell_type.hex;

import network.something.somepotter.spell_type.SpellType;
import network.something.somepotter.util.ColorUtil;

public class HexType extends SpellType {
    public static String ID = "hex";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public ColorUtil getColor() {
        return ColorUtil.HEX;
    }
}
