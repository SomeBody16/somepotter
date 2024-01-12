package network.something.somepotter.spell.circumrota;

import network.something.somepotter.cast.Cast;
import network.something.somepotter.cast.projectile.ProjectileCast;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import org.jetbrains.annotations.NotNull;

/**
 * Rotates the target
 * <p>
 * Will turn entity 180 degrees
 * Will not work on blocks
 */
public class CircumrotaSpell extends Spell {
    public static final String ID = "circumrota";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    @NotNull
    public SpellListener<CircumrotaSpell> getListener() {
        return new CircumrotaListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileCast();
    }
}
