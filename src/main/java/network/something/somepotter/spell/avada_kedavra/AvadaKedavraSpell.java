package network.something.somepotter.spell.avada_kedavra;

import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.util.ColorUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Kills the target
 */
public class AvadaKedavraSpell extends Spell {

    public static final String ID = "avada_kedavra";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull() SpellListener<AvadaKedavraSpell> getListener() {
        return new AvadaKedavraListener();
    }

    @Override
    public ColorUtil getColor() {
        return ColorUtil.UNFORGIVEABLE;
    }
}
