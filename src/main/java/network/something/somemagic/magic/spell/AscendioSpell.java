package network.something.somemagic.magic.spell;

import net.minecraft.world.entity.LivingEntity;
import network.something.somemagic.magic.spell.core.SelfSpell;
import network.something.somemagic.util.SpellColor;

public class AscendioSpell extends SelfSpell {
    public static final String ID = "ascendio";

    public AscendioSpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public int getCooldownTicks() {
        return 20 * 5;
    }


    @Override
    public SpellColor getColor() {
        return SpellColor.FORCE;
    }

    @Override
    public void onCast() {
        var motion = caster.getDeltaMovement().add(0, 2, 0);
        caster.setDeltaMovement(motion);
        caster.fallDistance = 0;
        caster.hurtMarked = true;
    }
}

