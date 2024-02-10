package network.something.somepotter.spells.cast.touch;

import network.something.somepotter.effect.Effects;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spells.cast.Cast;

public class TouchCast extends Cast {
    public static final String ID = "touch";

    public int range = 32;
    public boolean canHitFluid = false;

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void execute() {
        var result = caster.pick(range, 1.0F, canHitFluid);

        var cancelled = SpellHitEvent.publish(spell, caster, level, abilityPower, areaOfEffect, result);
        if (!cancelled) {
            Effects.touch(level, result.getLocation(), spell.getColor());
        }
    }

    public TouchCast setRange(int range) {
        this.range = range;
        return this;
    }

    public TouchCast canHitFluid(boolean canHitFluid) {
        this.canHitFluid = canHitFluid;
        return this;
    }

    public TouchCast canHitFluid() {
        return canHitFluid(true);
    }

}
