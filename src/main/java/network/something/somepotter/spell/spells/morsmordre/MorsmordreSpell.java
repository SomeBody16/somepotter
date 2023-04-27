package network.something.somepotter.spell.spells.morsmordre;

import net.minecraft.sounds.SoundEvent;
import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.spell.type.SpellType;
import network.something.somepotter.spell.type.SpellTypes;
import network.something.somepotter.util.SpellColor;

import java.util.List;

public class MorsmordreSpell extends AbstractSpell {
    public static final String ID = "morsmordre";

    public static final float MARK_SIZE = 4.0f;

    public static final List<?> init = List.of(
            new MorsmordreCastListener(),
            new MorsmordreHitListener()
    );

    public MorsmordreSpell() {
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
    public SoundEvent getSound() {
        return null;
    }

    @Override
    public int getCooldown() {
        return 20 * 2;
    }
}
