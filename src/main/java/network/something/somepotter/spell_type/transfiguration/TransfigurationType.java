package network.something.somepotter.spell_type.transfiguration;

import network.something.somepotter.spell_type.SpellType;
import network.something.somepotter.util.ColorUtil;

public class TransfigurationType extends SpellType {

    public static final String ID = "transfiguration";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public ColorUtil getColor() {
        return ColorUtil.TRANSFIGURATION;
    }
}
