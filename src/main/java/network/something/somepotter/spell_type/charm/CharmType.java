package network.something.somepotter.spell_type.charm;

import network.something.somepotter.spell_type.SpellType;
import network.something.somepotter.util.ColorUtil;

public class CharmType extends SpellType {

    public static final String ID = "charm";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public ColorUtil getColor() {
        return ColorUtil.CHARM;
    }
}
