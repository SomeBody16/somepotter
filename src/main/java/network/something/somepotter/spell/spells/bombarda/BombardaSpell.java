package network.something.somepotter.spell.spells.bombarda;

import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class BombardaSpell extends AbstractSpell {

    public static final BombardaSpell BOMBARDA = new BombardaSpell("bombarda", 4.0f);
    public static final BombardaSpell BOMBARDA_MAXIMA = new BombardaSpell("bombarda_maxima", 8.0f);

    public final float explosionSize;

    public static final List<?> init = List.of(
            new BombardaCastListener(),
            new BombardaHitListener()
    );

    private BombardaSpell(String id, float explosionSize) {
        super(id);
        this.explosionSize = explosionSize;
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.DAMAGE;
    }

    @Override
    public int getCooldown() {
        var ratio = explosionSize / 4.0f;
        return (int) (20 * 60 * ratio);
    }
}
