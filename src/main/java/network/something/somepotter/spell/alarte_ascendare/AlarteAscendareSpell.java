package network.something.somepotter.spell.alarte_ascendare;

import network.something.somepotter.cast.Cast;
import network.something.somepotter.cast.projectile.ProjectileCast;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.util.ColorUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Shoots the target high into the air
 */
public class AlarteAscendareSpell extends Spell {
    public static final String ID = "alarte_ascendare";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<AlarteAscendareSpell> getListener() {
        return new AlarteAscendareListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileCast();
    }

    @Override
    public ColorUtil getColor() {
        return ColorUtil.FORCE;
    }
}
