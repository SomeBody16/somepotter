package network.something.somepotter.spell.aguamenti;

import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.util.ColorUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Creates a jet of water from the caster's wand
 * <p>
 * If cast at block, it will extinguish fire if possible, otherwise will create water source block
 * If cast at entity, it will extinguish fire if possible
 */
public class AguamentiSpell extends Spell {

    public static final String ID = "aguamenti";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public float getAreaOfEffect() {
        return 2F;
    }

    @Override
    public ColorUtil getColor() {
        return ColorUtil.UTILITY;
    }

    @Override
    public @NotNull SpellListener<AguamentiSpell> getListener() {
        return new AguamentiListener();
    }
}
