package network.something.somepotter.spell.spells.avada_kedavra;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.spell.type.SpellType;
import network.something.somepotter.spell.type.SpellTypes;
import network.something.somepotter.util.DamageSourceUtil;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class AvadaKedavraSpell extends AbstractSpell {
    public static final String ID = "avada_kedavra";


    public static final List<?> init = List.of(
            new AvadaKedavraCastListener(),
            new AvadaKedavraHitListener()
    );

    public static DamageSource getDamageSource(Entity caster) {
        return DamageSourceUtil.indirect("spell." + ID, caster);
    }

    public AvadaKedavraSpell() {
        super(ID);
    }

    @Override
    public SpellType getType() {
        return SpellTypes.CURSE;
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.UNFORGIVEABLE;
    }

    @Override
    public int getCooldown() {
        return 20 * 60 * 2;
    }
}
