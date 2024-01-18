package network.something.somepotter.spells.cast.self;

import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spells.cast.Cast;

public class SelfCast extends Cast {
    public static final String ID = "self";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void execute() {
        var hitResult = new EntityHitResult(caster);
        SpellHitEvent.publish(spell, caster, level, abilityPower, areaOfEffect, hitResult);
    }
}
