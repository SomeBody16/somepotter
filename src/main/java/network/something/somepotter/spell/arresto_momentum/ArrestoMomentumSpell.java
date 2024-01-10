package network.something.somepotter.spell.arresto_momentum;

import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import org.jetbrains.annotations.NotNull;

/**
 * Slows the target's momentum
 * <p>
 * Applies a slowness and slow falling effect to the target
 */
public class ArrestoMomentumSpell extends Spell {

    public static final String ID = "arresto_momentum";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<ArrestoMomentumSpell> getListener() {
        return new ArrestoMomentumListener();
    }
}
