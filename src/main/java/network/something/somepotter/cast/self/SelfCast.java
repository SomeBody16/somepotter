package network.something.somepotter.cast.self;

import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.cast.Cast;
import network.something.somepotter.event.SpellHitEvent;

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
