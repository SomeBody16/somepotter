package network.something.somepotter.spells.spell.disillusio;

import net.minecraft.world.effect.MobEffectCategory;
import network.something.somepotter.spells.spell.SpellEffect;

public class DisillusioEffect extends SpellEffect<DisillusioSpell> {

    public DisillusioEffect() {
        super(MobEffectCategory.BENEFICIAL, DisillusioSpell.ID);
    }

}
