package network.something.somepotter.spell.ascendio;

import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.util.ColorUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Shoots the caster high into the air
 */
public class AscendioSpell extends Spell {

    public static final String ID = "ascendio";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<AscendioSpell> getListener() {
        return new AscendioListener();
    }

    @Override
    public ColorUtil getColor() {
        return ColorUtil.FORCE;
    }
}
