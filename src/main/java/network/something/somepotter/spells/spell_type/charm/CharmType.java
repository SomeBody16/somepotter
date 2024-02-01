package network.something.somepotter.spells.spell_type.charm;

import network.something.somepotter.spells.spell_type.SpellType;

import java.awt.*;

public class CharmType extends SpellType {

    public static final String ID = "charm";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Color getColor() {
        return new Color(0xFFC0CB);
    }

    @Override
    public int getBaseSkillPointCost() {
        return 2;
    }
}
