package network.something.somepotter.spell.aguamenti;

import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;

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
    public SpellListener<AguamentiSpell> getListener() {
        return new AguamentiListener();
    }
}
