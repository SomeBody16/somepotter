package network.something.somepotter.spell.spells.cistem_aperio;

import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class CistemAperioSpell extends AbstractSpell {
    public static final String ID = "cistem_aperio";

    public static final List<?> init = List.of(
            new CistemAperioCastListener(),
            new CistemAperioHitListener()
    );

    public CistemAperioSpell() {
        super(ID);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.DAMAGE;
    }
}
