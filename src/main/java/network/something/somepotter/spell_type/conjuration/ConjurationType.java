package network.something.somepotter.spell_type.conjuration;

import network.something.somepotter.spell_type.SpellType;
import network.something.somepotter.util.ColorUtil;

public class ConjurationType extends SpellType {
    public static String ID = "conjuration";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public ColorUtil getColor() {
        return ColorUtil.CONJURATION;
    }
}
