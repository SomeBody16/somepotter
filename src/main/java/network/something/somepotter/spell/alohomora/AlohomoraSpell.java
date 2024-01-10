package network.something.somepotter.spell.alohomora;

import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.util.ColorUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Opens locked doors
 * <p>
 * Open doors, trapdoors, fence gates, and iron doors
 */
public class AlohomoraSpell extends Spell {

    public static final String ID = "alohomora";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<AlohomoraSpell> getListener() {
        return new AlohomoraListener();
    }

    @Override
    public ColorUtil getColor() {
        return ColorUtil.ESSENTIAL;
    }
}
