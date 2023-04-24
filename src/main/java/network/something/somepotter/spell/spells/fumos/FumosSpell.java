package network.something.somepotter.spell.spells.fumos;

import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class FumosSpell extends AbstractSpell {
    public static final String ID = "fumos";

    public static final float RANGE = 5;
    public static final int PARTICLES_PER_BLOCK = 20;

    public static final List<?> init = List.of(
            new FumosCastListener(),
            new FumosHitListener()
    );

    public FumosSpell() {
        super(ID);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.UTILITY;
    }

    @Override
    public int getCooldown() {
        return 20 * 10;
    }
}
