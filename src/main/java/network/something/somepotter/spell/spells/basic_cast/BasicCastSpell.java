package network.something.somepotter.spell.spells.basic_cast;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.util.DamageSourceUtil;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class BasicCastSpell extends AbstractSpell {
    public static final String ID = "basic_cast";

    public static final BasicCastSpell instance = new BasicCastSpell();

    public static final float DAMAGE = 2.0f;

    public static final List<?> init = List.of(
            new BasicCastCastListener(),
            new BasicCastHitListener()
    );

    public static DamageSource getDamageSource(Entity caster) {
        return DamageSourceUtil.indirect("spell." + ID, caster)
                .setProjectile()
                .setMagic();
    }

    public BasicCastSpell() {
        super(ID);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.DAMAGE;
    }

    @Override
    public int getCooldown() {
        return (int) (20 * 1.5);
    }
}
