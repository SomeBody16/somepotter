package network.something.somepotter.spell.spells.confringo;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.util.DamageSourceUtil;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class ConfringoSpell extends AbstractSpell {
    public static final String ID = "confringo";

    public static final float SIZE = 4.0f;

    public static final List<?> init = List.of(
            new ConfringoCastListener(),
            new ConfringoHitListener()
    );

    public static DamageSource getDamageSource(Entity caster) {
        return DamageSourceUtil.indirect("spell." + ID, caster).setIsFire();
    }

    public ConfringoSpell() {
        super(ID);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.DAMAGE;
    }
}
