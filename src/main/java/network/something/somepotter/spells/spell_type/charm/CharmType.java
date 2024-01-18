package network.something.somepotter.spells.spell_type.charm;

import network.something.somepotter.spells.spell_type.SpellType;
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

    @Override
    public int getBaseSkillPointCost() {
        return 2;
    }
}
