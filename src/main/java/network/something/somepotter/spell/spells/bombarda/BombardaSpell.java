package network.something.somepotter.spell.spells.bombarda;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.util.DamageSourceUtil;
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

    public DamageSource getDamageSource(Entity caster) {
        return DamageSourceUtil.indirect("spell." + getId(), caster).setExplosion();
    }
}
