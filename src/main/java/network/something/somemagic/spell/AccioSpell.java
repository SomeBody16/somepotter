package network.something.somemagic.spell;

import net.minecraft.world.entity.LivingEntity;
import network.something.somemagic.util.SpellColor;

public class AccioSpell extends TouchSpell {
    public static final String ID = "accio";

    public AccioSpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public int getCooldownTicks() {
        return 15;
    }
    
    @Override
    public SpellColor getColor() {
        return SpellColor.FORCE;
    }
}

