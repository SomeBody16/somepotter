package network.something.somepotter.spell.spells.crucio;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.util.DamageSourceUtil;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class CrucioSpell extends AbstractSpell {
    public static final String ID = "crucio";

    public static final double RANGE = 16;
    public static final double LOOK_THRESHOLD = 0.9;
    public static final int REFRESH_DURATION = 20 * 2;

    public static final List<?> init = List.of(
            new CrucioCastListener(),
            new CrucioHitListener()
    );

    public static DamageSource getDamageSource(Entity caster) {
        return DamageSourceUtil.indirect("spell." + ID, caster).setMagic();
    }

    public CrucioSpell() {
        super(ID);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.UNFORGIVEABLE;
    }
}
